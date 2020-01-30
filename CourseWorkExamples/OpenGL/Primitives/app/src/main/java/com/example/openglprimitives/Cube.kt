package com.example.openglprimitives

import javax.microedition.khronos.opengles.GL10

class Cube : Drawable {
    private val scale = 0.618f
    private val vertexBuffer = BufferHelper.directFloat(
        -1f, -1f, -1f,
        1f, -1f, -1f,
        1f, -1f, 1f,
        -1f, -1f, 1f,
        -1f, 1f, -1f,
        1f, 1f, -1f,
        1f, 1f, 1f,
        -1f, 1f, 1f
    )

    private val colorBuffer = BufferHelper.directFloat(
            0.12f, 0.86f, 0.22f, 0.5f,
            0.89f, 0.07f, 0.11f, 0.5f,
            0.67f, 0.59f, 0.07f, 0.5f,
            0.10f, 0.81f, 0.39f, 0.5f,
            0.78f, 0.44f, 0.05f, 0.5f,
            0.07f, 0.55f, 0.71f, 0.5f,
            0.14f, 0.30f, 0.84f, 0.5f,
            0.53f, 0.09f, 0.72f, 0.5f
        )

    private val indexBuffer = BufferHelper.directByte(
        0, 1, 2,
        0, 2, 3,   // bottom

        0, 1, 5,
        0, 5, 4,   // front

        3, 0, 4,
        3, 4, 7,   // left

        2, 3, 7,
        2, 7, 6,   // rear

        1, 2, 6,
        1, 6, 5,   // right

        4, 5, 6,
        4, 6, 7    // top
    )

    private val startTime = System.currentTimeMillis()

    override fun draw(gl: GL10) {
        gl.run {
            val time = (System.currentTimeMillis() - startTime) / 1000f
            glTranslatef(0f, 0f, -3f)
            glRotatef(time * 120f, 1f, 0f, 0f)
            glRotatef(time * 50f, 0f, 1f, 0f)
            glScalef(scale, scale, scale)

            glEnableClientState(GL10.GL_VERTEX_ARRAY)
            glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)

            glEnableClientState(GL10.GL_COLOR_ARRAY)
            glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer)

            //glEnable(GL10.GL_DEPTH_TEST)
            glEnable(GL10.GL_BLEND)
            glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA)
            glDepthFunc(GL10.GL_LESS)

            glDrawElements(GL10.GL_TRIANGLES, 6 * 6, GL10.GL_UNSIGNED_BYTE, indexBuffer)

            glDisableClientState(GL10.GL_VERTEX_ARRAY)
            glDisableClientState(GL10.GL_COLOR_ARRAY)
        }
    }
}