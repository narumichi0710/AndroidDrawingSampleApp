package com.example.androiddrawingsampleapp.entity


data class Coordinate(
    var x: Double = 0.0,
    var y: Double = 0.0,
    var info: Info? = null
) {
    data class Info(
        var interval: Double? = null,
        var text: String? = null
    )
}