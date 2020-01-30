package com.example.phonglighting

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLUtils
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.opengl.GLES20.*
import java.io.FileInputStream
import java.io.InputStream


class Texture2D {
    private val id: Int

    fun use(i: Int) {
        glActiveTexture(i)
        glBindTexture(GL_TEXTURE_2D, id)
    }

    constructor(context: Context, resourceId: Int) {
        id = loadTexture(context, resourceId)
    }

    constructor(fileStream: InputStream) {
        id = loadTexture(fileStream)
    }

    constructor(bitmap: Bitmap) {
        id = loadTexture(bitmap)
    }

    fun loadTexture(bitmap: Bitmap): Int {
        // создание объекта текстуры
        val textureIds = IntArray(1)
        glGenTextures(1, textureIds, 0)
        if (textureIds[0] == 0) {
            return 0
        }
//        glDeleteTextures(1, textureIds, 0)
        // настройка объекта текстуры
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, textureIds[0])

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)

        bitmap.recycle()

        return textureIds[0]
    }

    fun loadTexture(context: Context, resourceId: Int): Int {
        var result = 0
        BitmapFactory.decodeResource(context.getResources(), resourceId)?.let{
            result = loadTexture(it)

        }
        return result
    }

    fun loadTexture(fileStream: InputStream): Int {
        var result = 0
        BitmapFactory.decodeStream(fileStream)?.let {
            result = loadTexture(it)
        }
        return result
    }


//        val array = IntArray(1)
//        glGenTextures(1, array, 0)
//        val tex = array[0]
//
//        glBindTexture(GL_TEXTURE_2D, tex)
//        glTexParameterf(
//            GL_TEXTURE_2D,
//            GL_TEXTURE_MIN_FILTER,
//            GL_LINEAR.toFloat()
//        )
//
//        val bmp = BitmapFactory.decodeResource(context.resources, resourceId)
//        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bmp, 0)
//        bmp.recycle()
//
//        return tex

}