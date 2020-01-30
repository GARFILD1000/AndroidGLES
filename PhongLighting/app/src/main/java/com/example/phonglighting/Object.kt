package com.example.phonglighting

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES20.*
import android.opengl.GLUtils

abstract class Object{
    data class ObjectData (
        var vertices: FloatArray,
        var normals: FloatArray,
        var indices: ShortArray,
        var texCoords: FloatArray
    )
    abstract var data: ObjectData
    var shader: Shader? = null
    var texture: Texture2D? = null

    abstract fun draw()
}