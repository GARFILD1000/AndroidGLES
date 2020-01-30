package com.example.modelviewer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES20.*
import android.opengl.GLUtils

abstract class Object{
    data class ObjectData (
        val vertices: FloatArray,
        val normals: FloatArray,
        val indices: ShortArray,
        val texCoords: FloatArray
    )
    abstract var data: ObjectData
    var shader: Shader? = null
    var texture: Texture2D? = null


    abstract fun draw()
}