package com.example.androiddrawingsampleapp.data


import com.example.androiddrawingsampleapp.entity.DrawingArrowObjectEntity
import com.example.androiddrawingsampleapp.entity.DrawingCircleObjectEntity
import com.example.androiddrawingsampleapp.entity.DrawingLayerEntity
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

class DrawingLayerData(
    val id: UUID = UUID.randomUUID(),
    objects: List<DrawingObjectData> = emptyList()
) {
    private val _objects: MutableList<DrawingObjectData> = objects.toMutableList()
    val objects: List<DrawingObjectData> get() = _objects
    var editingObject: DrawingObjectData? = null

    private val _modified = MutableStateFlow(Date())
    val modified: StateFlow<Date> = _modified.asStateFlow()

    constructor(entity: DrawingLayerEntity) : this(id = entity.id) {
        val pencilObjects = entity.pencilObjects.map { DrawingPencilObjectData(it) }
        val arrowObjects = entity.arrowObjects.map { DrawingArrowObjectData(it) }
        val rectangleObjects = entity.rectangleObjects.map { DrawingRectangleObjectData(it) }
        val circleObjects = entity.circleObjects.map { DrawingCircleObjectData(it) }
        val textObjects = entity.textObjects.map { DrawingTextObjectData(it) }

        _objects.addAll(pencilObjects)
        _objects.addAll(arrowObjects)
        _objects.addAll(rectangleObjects)
        _objects.addAll(circleObjects)
        _objects.addAll(textObjects)
    }

    fun appendEditingObject() {
        editingObject?.let {
            _objects.add(it)
            editingObject = null
            notifyModified()
        }
    }

    fun update(layer: DrawingLayerData) {
        layer.objects.forEach { newObject ->
            _objects.find { it.id == newObject.id }?.let { existingObject ->
                val index = _objects.indexOf(existingObject)
                _objects[index] = newObject
            } ?: _objects.add(newObject)
        }
        notifyModified()
    }

    fun apply() {
        _modified.value = Date()
    }

    fun reset() {
        _objects.clear()
        apply()
    }

    fun append(objectData: DrawingObjectData) {
        _objects.add(objectData)
        notifyModified()
    }

    fun update(objectData: DrawingObjectData) {
        val index = _objects.indexOfFirst { it.id == objectData.id }
        if (index != -1) {
            _objects[index] = objectData
            notifyModified()
        }
    }

    fun delete(id: UUID) {
        _objects.find { it.id == id }?.let { objectData ->
            objectData.updateState(DrawingObjectState.Deleted)
            notifyModified()
        }
    }

    private fun notifyModified() {
        _modified.value = Date()
    }

    override fun equals(other: Any?): Boolean {
        return (other is DrawingLayerData) && this.id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun toEntity(): DrawingLayerEntity {
        val pencilEntities = mutableListOf<DrawingPencilObjectEntity>()
        var arrowEntities = mutableListOf<DrawingArrowObjectEntity>()
        var rectangleEntities = mutableListOf<DrawingRectangleObjectEntity>()
        var circleEntities = mutableListOf<DrawingCircleObjectEntity>()
        var textEntities = mutableListOf<DrawingTextObjectEntity>()

        objects.forEach {
            when (it.type) {
                DrawingObjectType.Pencil -> pencilEntities.add(it.data as DrawingPencilObjectEntity)
                DrawingObjectType.ArrowLine -> arrowEntities.add(it.data as DrawingArrowObjectEntity)
                DrawingObjectType.Rectangle -> rectangleEntities.add(it.data as DrawingRectangleObjectEntity)
                DrawingObjectType.Circle -> circleEntities.add(it.data as DrawingCircleObjectEntity)
                DrawingObjectType.Text -> textEntities.add(it.data as DrawingTextObjectEntity)
            }
        }

        return DrawingLayerEntity(
            id = id,
            pencilObjects = pencilEntities,
            arrowObjects = arrowEntities,
            rectangleObjects = rectangleEntities,
            circleObjects = circleEntities,
            textObjects = textEntities
        )
    }
}
