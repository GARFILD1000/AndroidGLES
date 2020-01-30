package com.example.watersurface

import android.opengl.GLES20.*

class Plane(val texture: Texture2D, val shader: Shader) {
    private val vertexBuffer = BufferHelper.directFloat(
        -1f, 1f, -1f,
        -1f, -1f, -1f,
        1f, -1f, -1f,
        1f, 1f, -1f
    )

    private val texcoordBuffer = BufferHelper.directFloat(
        0f, 1f,
        0f, 0f,
        1f, 0f,
        1f, 1f
    )

    fun draw() {
        shader.use()
        shader.setTexture("texture", 0, texture)
        shader.setVertexAttribute("position", 3, GL_FLOAT, false, 0, vertexBuffer)
        shader.setVertexAttribute("texcoord", 2, GL_FLOAT, false, 0, texcoordBuffer)
        glDrawArrays(GL_TRIANGLE_FAN, 0, 4)
    }
}