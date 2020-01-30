package com.example.opnegl_lab1

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.util.*
import javax.microedition.khronos.opengles.GL10

class Cube : Shape(){
    private val vertex = floatArrayOf(
        -1f, 1f, 1f,
        -1f, -1f, 1f,
        1f, -1f, 1f,
        1f, 1f, 1f, //front

        -1f, 1f, -1f,
        -1f, -1f, -1f,
        1f, -1f, -1f,
        1f, 1f, -1f //back

    )

    private val colors = floatArrayOf(
        1f, 1f, 0f, 1f,
        1f, 1f, 0f, 1f,
        1f, 1f, 0f, 1f,
        1f, 1f, 0f, 1f,

        1f, 0f, 1f, 1f,
        1f, 0f, 1f, 1f,
        1f, 0f, 1f, 1f,
        1f, 0f, 1f, 1f

    )

    private val indices = byteArrayOf(
        0, 1, 2, 0, 2, 3,   // front
        0, 1, 5, 0, 5, 4,   // left
        3, 0, 4, 3, 4, 7,   // top
        2, 3, 7, 2, 7, 6,   // right
        1, 2, 6, 1, 6, 5,   // bottom
        4, 5, 6, 4, 6, 7    // back
    )

    private val vertexBuffer: FloatBuffer = initBuffer(vertex)
    var colorsBuffer: FloatBuffer = initBuffer(colors)
    var indexBuffer: ByteBuffer = ByteBuffer.allocateDirect(indices.size)
        .order(ByteOrder.nativeOrder())
        .put(indices)
        .position(0) as ByteBuffer


    override fun draw(gl: GL10?) {
        super.draw(gl)
        gl?.run {
            glEnableClientState(GL10.GL_VERTEX_ARRAY)
            glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)
            glEnableClientState(GL10.GL_COLOR_ARRAY)
            glColorPointer(4, GL10.GL_FLOAT, 0, colorsBuffer)

            glEnable(GL10.GL_DEPTH_TEST)
            glDepthFunc(GL10.GL_LESS)
            glDrawElements(GL10.GL_TRIANGLES, 6 * 6 , GL10.GL_UNSIGNED_BYTE, indexBuffer)
            //glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4)

            glDisableClientState(GL10.GL_VERTEX_ARRAY)
            glDisableClientState(GL10.GL_COLOR_ARRAY)
        }

    }
}
