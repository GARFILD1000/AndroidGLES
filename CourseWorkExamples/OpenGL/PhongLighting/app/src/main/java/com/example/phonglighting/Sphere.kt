package com.example.phonglighting

import android.opengl.GLES20
import android.opengl.GLES20.*
import android.opengl.GLUtils

class Sphere(radius: Float, n: Int, val shader: Shader) {
    private val data = SphereGenerator.generate(radius, n)

    private val vertexBuffer = BufferHelper.directFloat(*data.vertices)
    private val normalBuffer = BufferHelper.directFloat(*data.normals)
    private val indexBuffer = BufferHelper.directShort(*data.indices)

    fun draw() {
        shader.use()
        shader.setVertexAttribute("position", 3, GL_FLOAT, false, 0, vertexBuffer)
        shader.setVertexAttribute("normal", 3, GL_FLOAT, true, 0, normalBuffer)

        glDrawElements(GL_TRIANGLES, data.indices.size, GL_UNSIGNED_SHORT, indexBuffer)
    }
}