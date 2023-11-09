package com.example.androiddrawingsampleapp.entity

import java.util.UUID

interface DrawingObjectProperty {
    var id: UUID
    var type: DrawingObjectType
    var state: DrawingObjectState
    var start: Coordinate
    var end: Coordinate
    val color: DrawingObjectColor
    var trajectory: List<Coordinate>
}

// ペン
data class DrawingPencilObjectEntity(
    override var id: UUID,
    override var type: DrawingObjectType,
    override var state: DrawingObjectState,
    override var start: Coordinate,
    override var end: Coordinate,
    override val color: DrawingObjectColor,
    override var trajectory: List<Coordinate>,
    var width: Double
) : DrawingObjectProperty

// 矢印
data class DrawingArrowObjectEntity(
    override var id: UUID,
    override var type: DrawingObjectType,
    override var state: DrawingObjectState,
    override var start: Coordinate,
    override var end: Coordinate,
    override var color: DrawingObjectColor,
    override var trajectory: List<Coordinate>,
    var width: Double
) : DrawingObjectProperty

// 矩形
data class DrawingRectangleObjectEntity(
    override var id: UUID,
    override var type: DrawingObjectType,
    override var state: DrawingObjectState,
    override var start: Coordinate,
    override var end: Coordinate,
    override var color: DrawingObjectColor,
    override var trajectory: List<Coordinate>,
    var width: Double
) : DrawingObjectProperty

// 円
data class DrawingCircleObjectEntity(
    override var id: UUID,
    override var type: DrawingObjectType,
    override var state: DrawingObjectState,
    override var start: Coordinate,
    override var end: Coordinate,
    override var color: DrawingObjectColor,
    override var trajectory: List<Coordinate>,
    var width: Double
) : DrawingObjectProperty

// テキスト
data class DrawingTextObjectEntity(
    override var id: UUID,
    override var type: DrawingObjectType,
    override var state: DrawingObjectState,
    override var start: Coordinate,
    override var end: Coordinate,
    override var color: DrawingObjectColor,
    override var trajectory: List<Coordinate>,
    var backgroundColor: DrawingObjectColor,
    var text: String
) : DrawingObjectProperty
