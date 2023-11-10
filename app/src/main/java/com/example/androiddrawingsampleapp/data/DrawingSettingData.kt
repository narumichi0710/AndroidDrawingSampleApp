package com.example.androiddrawingsampleapp.data

import android.graphics.PointF
import android.util.SizeF
import com.example.androiddrawingsampleapp.entity.Coordinate
import com.example.androiddrawingsampleapp.entity.DrawingObjectColor
import com.example.androiddrawingsampleapp.entity.DrawingObjectType

data class DrawingSettingData(
    var canvasSize: SizeF = SizeF(0f, 0f),
    var imageSize: SizeF = SizeF(0f, 0f),
    var scale: Float = 0f,
    var offset: PointF = PointF(0f, 0f),

    var type: DrawingObjectType = DrawingObjectType.Pencil,
    var color: DrawingObjectColor = DrawingObjectColor.Blue,
    var width: Double = 5.0,
    var text: String = "",
    var tmpCoordinate: Coordinate = Coordinate()
)