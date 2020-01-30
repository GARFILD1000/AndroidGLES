package com.example.openglprimitives

import javax.microedition.khronos.opengles.GL10

class Square : Drawable {
    private val scale = 0.618f
    private val vertexBuffer = BufferHelper.directFloat(
        -1f, 1f, 0f,
        -1f, -1f, 0f,
        1f, -1f, 0f,
        1f, 1f, 0f
    )

    //private val startTime = System.currentTimeMillis()

    override fun draw(gl: GL10) {
        gl.run {
            glTranslatef(0f, 0f, -3f)
            //glRotatef((System.currentTimeMillis() - startTime)/1000f * 360f, 1f, 0f, 0f)
            glScalef(scale, scale, scale)
            glColor4f(0.04f, 0.66f, 0.38f, 1f)

            glEnableClientState(GL10.GL_VERTEX_ARRAY)
            glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)

            glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4)

            glDisableClientState(GL10.GL_VERTEX_ARRAY)
        }
    }
}