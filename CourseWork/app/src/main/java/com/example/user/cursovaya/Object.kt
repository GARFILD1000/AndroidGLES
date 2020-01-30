package com.example.user.cursovaya;

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.PointF
import android.opengl.GLES20
import android.opengl.GLUtils

class Object(
        val context: Context,
        private val objResId: Int,
        private val textureResId: Int
) {
    private val objLoader: ObjectParser = ObjectParser(context, objResId)

    val vertexBuffer = BufferHelper.directFloat(*objLoader.positions)
    val normalBuffer = BufferHelper.directFloat(*objLoader.normals)
    val texcoordBuffer = BufferHelper.directFloat(*objLoader.texcoords)

    val vertexCount = objLoader.positions.size / 3

    var scale = arrayOf(1f, 1f, 1f)
    var rotation = arrayOf(0f, 0f, 0f)
    val translation = arrayOf(0f, 0f, 0f)

    var texture = IntArray(1)

    fun loadTexture() {
        GLES20.glGenTextures(1, texture, 0)
        val stream = context.resources.openRawResource(textureResId)
        val bitmap = BitmapFactory.decodeStream(stream)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[0])
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR.toFloat())
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)
        bitmap.recycle()
    }

}
