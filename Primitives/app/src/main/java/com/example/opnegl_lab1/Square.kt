package com.example.opnegl_lab1

import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10


class Square : Shape() {

    private val vertex = floatArrayOf(
        -1f, 1f, 0f,
        -1f, -1f, 0f,
        1f, -1f, 0f,
        1f, 1f, 0f
    )

    private var colors = floatArrayOf(
        1f, 1f, 0f, -1f,
        1f, 0f, 1f, -1f,
        0f, 1f, 1f, -1f,
        0f, 1f, 0f, -1f
    )

    var vertexBuffer: FloatBuffer? = initBuffer(vertex)
    var colorsBuffer: FloatBuffer? = initBuffer(colors)

    override fun draw(gl: GL10?) {
        super.draw(gl)
        gl?.run {
            glClear(GL10.GL_COLOR_BUFFER_BIT)
            glEnableClientState(GL10.GL_VERTEX_ARRAY)
            glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)
            glEnableClientState(GL10.GL_COLOR_ARRAY)
            glColorPointer(4, GL10.GL_FLOAT, 0, colorsBuffer)
            glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4)
            glDisableClientState(GL10.GL_VERTEX_ARRAY)
            glDisableClientState(GL10.GL_COLOR_ARRAY)
        }
    }

}