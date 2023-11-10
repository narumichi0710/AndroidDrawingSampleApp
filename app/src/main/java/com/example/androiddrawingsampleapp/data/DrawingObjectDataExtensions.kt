package com.example.androiddrawingsampleapp.data


fun DrawingObjectData.asPencil(): DrawingPencilObjectData? = this as? DrawingPencilObjectData
fun DrawingObjectData.asArrow(): DrawingArrowObjectData? = this as? DrawingArrowObjectData
fun DrawingObjectData.asRectangle(): DrawingRectangleObjectData? = this as? DrawingRectangleObjectData
fun DrawingObjectData.asCircle(): DrawingCircleObjectData? = this as? DrawingCircleObjectData
fun DrawingObjectData.asText(): DrawingTextObjectData? = this as? DrawingTextObjectData
