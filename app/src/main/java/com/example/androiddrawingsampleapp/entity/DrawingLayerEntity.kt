package com.example.androiddrawingsampleapp.entity

import java.util.UUID


data class DrawingLayerEntity(
    var id: UUID = UUID.randomUUID(),
    var pencilObjects: List<DrawingPencilObjectEntity> = emptyList(),
    var arrowObjects: List<DrawingArrowObjectEntity> = emptyList(),
    var rectangleObjects: List<DrawingRectangleObjectEntity> = emptyList(),
    var circleObjects: List<DrawingCircleObjectEntity> = emptyList(),
    var textObjects: List<DrawingTextObjectEntity> = emptyList()
)