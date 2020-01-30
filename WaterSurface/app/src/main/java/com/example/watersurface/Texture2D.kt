package com.example.watersurface

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLES20.*
import android.opengl.GLUtils

class Texture2D(name: Int, context: Context) {
    private val id: Int = load(name, context)

    fun use(i: Int) {
        glActiveTexture(i)
        glBindTexture(GL_TEXTURE_2D, id)
    }

    private fun load(name: Int, context: Context): Int {
        val array = IntArray(1)
        glGenTextures(1, array, 0)
        val tex = array[0]

        glBindTexture(GL_TEXTURE_2D, tex)
        glTexParameterf(
            GL_TEXTURE_2D,
            GL_TEXTURE_MIN_FILTER,
            GL_LINEAR.toFloat()
        )

        val bmp = BitmapFactory.decodeResource(context.resources, name)
        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bmp, 0)
        bmp.recycle()

        return tex
    }
}