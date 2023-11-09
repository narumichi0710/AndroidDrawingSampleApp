package com.example.androiddrawingsampleapp.entity


enum class DrawingObjectState(val value: String) {
    Created("created"),
    Updated("updated"),
    Deleted("deleted"),
    Synced("synced");
}