package com.example.modelviewer

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLUtils
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.opengl.GLES20.*


class Texture2D(context: Context, resourceId: Int) {
    private val id: Int = loadTexture(context, resourceId)

    fun use(i: Int) {
        glActiveTexture(i)
        glBindTexture(GL_TEXTURE_2D, id)
    }

    fun loadTexture(context: Context, resourceId: Int): Int {
        // создание объекта текстуры
        val textureIds = IntArray(1)
        glGenTextures(1, textureIds, 0)
        if (textureIds[0] == 0) {
            return 0
        }

        val bitmap = BitmapFactory.decodeResource(
            context.getResources(), resourceId
        )

        if (bitmap == null) {
            glDeleteTextures(1, textureIds, 0)
            return 0
        }

        // настройка объекта текстуры
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, textureIds[0])

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)

        bitmap.recycle()

        return textureIds[0]
    }
}