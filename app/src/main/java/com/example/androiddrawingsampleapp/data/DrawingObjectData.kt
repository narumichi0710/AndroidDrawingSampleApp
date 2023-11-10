package com.example.androiddrawingsampleapp.data

import com.example.androiddrawingsampleapp.entity.Coordinate
import com.example.androiddrawingsampleapp.entity.DrawingArrowObjectEntity
import com.example.androiddrawingsampleapp.entity.DrawingCircleObjectEntity
import com.example.androiddrawingsampleapp.entity.DrawingObjectColor
import com.example.androiddrawingsampleapp.entity.DrawingObjectProperty
import com.example.androiddrawingsampleapp.entity.DrawingObjectState
import com.example.androiddrawingsampleapp.entity.DrawingObjectType
import com.example.androiddrawingsampleapp.entity.DrawingPencilObjectEntity
import com.example.androiddrawingsampleapp.entity.DrawingRectangleObjectEntity
import com.example.androiddrawingsampleapp.entity.DrawingTextObjectEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date
import java.util.UUID

open class DrawingObjectData(var data: DrawingObjectProperty) {
    val id: UUID get() = data.id
    val type: DrawingObjectType get() = data.type
    open var state: DrawingObjectState
        get() = data.state
        set(value) { data.state = value }
    open var start: Coordinate
        get() = data.start
        set(value) { data.start = value }
    open var end: Coordinate
        get() = data.end
        set(value) { data.end = value }
    val color: DrawingObjectColor get() = data.color

    private val _trajectory: MutableList<Coordinate> = data.trajectory.toMutableList()
    val trajectory: List<Coordinate> get() = _trajectory

    private val _modified = MutableStateFlow<Date?>(null)
    val modified: StateFlow<Date?> = _modified.asStateFlow()

    protected open fun notifyModified() {
        _modified.value = Date()
    }

    fun onStart(value: Coordinate) {
        start = value
    }

    fun onAddTrajectory(coordinate: Coordinate) {
        _trajectory.add(coordinate)
    }

    fun onUpdate(start: Coordinate, end: Coordinate) {
        this.start = start
        this.end = end
        notifyModified()
    }

    fun onEnd(value: Coordinate) {
        end = value
        notifyModified()
    }

    fun updateState(value: DrawingObjectState) {
        state = value
    }
}


// ペン
class DrawingPencilObjectData : DrawingObjectData {
    var width: Double

    constructor(entity: DrawingPencilObjectEntity) : super(entity) {
        width = entity.width
    }

    constructor(pencil: DrawingPencilObjectData) : super(pencil.data) {
        width = pencil.width
    }

    companion object {
        fun create(setting: DrawingSettingData, coordinate: Coordinate): DrawingPencilObjectData {
            val entity = DrawingPencilObjectEntity(
                id = UUID.randomUUID(),
                type = DrawingObjectType.Pencil,
                state = DrawingObjectState.Created,
                start = coordinate,
                end = coordinate,
                color = setting.color,
                width = setting.width,
                trajectory = mutableListOf(coordinate)
            )
            return DrawingPencilObjectData(entity)
        }
    }
}


// 矢印
class DrawingArrowObjectData : DrawingObjectData {
    var width: Double

    constructor(entity: DrawingArrowObjectEntity) : super(entity) {
        width = entity.width
    }

    constructor(arrow: DrawingArrowObjectData) : super(arrow.data) {
        width = arrow.width
    }

    companion object {
        fun create(setting: DrawingSettingData, coordinate: Coordinate): DrawingArrowObjectData {
            val entity = DrawingArrowObjectEntity(
                id = UUID.randomUUID(),
                type = DrawingObjectType.ArrowLine,
                state = DrawingObjectState.Created,
                start = coordinate,
                end = coordinate,
                color = setting.color,
                width = setting.width,
                trajectory = mutableListOf(coordinate)
            )
            return DrawingArrowObjectData(entity)
        }
    }
}

// 矩形
class DrawingRectangleObjectData : DrawingObjectData {
    var width: Double

    constructor(entity: DrawingRectangleObjectEntity) : super(entity) {
        width = entity.width
    }

    constructor(rectangle: DrawingRectangleObjectData) : super(rectangle.data) {
        width = rectangle.width
    }

    companion object {
        fun create(setting: DrawingSettingData, coordinate: Coordinate): DrawingRectangleObjectData {
            val entity = DrawingRectangleObjectEntity(
                id = UUID.randomUUID(),
                type = DrawingObjectType.Rectangle,
                state = DrawingObjectState.Created,
                start = coordinate,
                end = coordinate,
                color = setting.color,
                width = setting.width,
                trajectory = mutableListOf(coordinate)
            )
            return DrawingRectangleObjectData(entity)
        }
    }
}

// 円
class DrawingCircleObjectData : DrawingObjectData {
    var width: Double

    constructor(entity: DrawingCircleObjectEntity) : super(entity) {
        width = entity.width
    }

    constructor(circle: DrawingCircleObjectData) : super(circle.data) {
        width = circle.width
    }

    companion object {
        fun create(setting: DrawingSettingData, coordinate: Coordinate): DrawingCircleObjectData {
            val entity = DrawingCircleObjectEntity(
                id = UUID.randomUUID(),
                type = DrawingObjectType.Circle,
                state = DrawingObjectState.Created,
                start = coordinate,
                end = coordinate,
                color = setting.color,
                width = setting.width,
                trajectory = mutableListOf(coordinate)
            )
            return DrawingCircleObjectData(entity)
        }
    }
}

// テキスト
class DrawingTextObjectData : DrawingObjectData {
    var backgroundColor: DrawingObjectColor
    var text: String

    constructor(entity: DrawingTextObjectEntity) : super(entity) {
        backgroundColor = entity.backgroundColor
        text = entity.text
    }

    constructor(text: DrawingTextObjectData) : super(text.data) {
        this.backgroundColor = text.backgroundColor
        this.text = text.text
    }

    companion object {
        fun create(setting: DrawingSettingData, coordinate: Coordinate, trajectory: List<Coordinate>): DrawingTextObjectData {
            val entity = DrawingTextObjectEntity(
                id = UUID.randomUUID(),
                type = DrawingObjectType.Text,
                state = DrawingObjectState.Created,
                start = coordinate,
                end = coordinate,
                color = setting.color,
                backgroundColor = getBackgroundColor(setting.color),
                text = setting.text,
                trajectory = trajectory.toMutableList()
            )
            return DrawingTextObjectData(entity)
        }

        fun getBackgroundColor(textColor: DrawingObjectColor): DrawingObjectColor {
            return when (textColor) {
                DrawingObjectColor.Blue -> DrawingObjectColor.Green
                DrawingObjectColor.Green -> DrawingObjectColor.Magenta
                DrawingObjectColor.Magenta -> DrawingObjectColor.Red
                DrawingObjectColor.Red -> DrawingObjectColor.Yellow
                DrawingObjectColor.Yellow -> DrawingObjectColor.Green
            }
        }
    }
}