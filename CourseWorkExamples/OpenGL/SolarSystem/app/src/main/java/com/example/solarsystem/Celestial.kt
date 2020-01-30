package com.example.solarsystem

import android.graphics.BitmapFactory
import android.opengl.GLUtils
import java.nio.FloatBuffer
import java.nio.ShortBuffer
import javax.microedition.khronos.opengles.GL10

class Celestial (
    gl: GL10,
    radius: Float,
    textureName: Int,
    renderer: SolarSystemRenderer
)  {
    private val n = 24

    private val vertexBuffer: FloatBuffer
    private val texCoordBuffer: FloatBuffer
    private val indexBuffer: ShortBuffer
    private val totalNumIndices: Int

    init {
        val sphereData = SphereGenerator.generate(radius, n)

        vertexBuffer = BufferHelper.directFloat(*sphereData.vertices)
        texCoordBuffer = BufferHelper.directFloat(*sphereData.texCoords)
        indexBuffer = BufferHelper.directShort(*sphereData.indices)

        totalNumIndices = sphereData.indices.size
    }

    private val texture = initTexture(textureName, gl, renderer)

    private fun initTexture(textureName: Int, gl: GL10, renderer: SolarSystemRenderer): Int {
        gl.run {
            val array = IntArray(1)
            glGenTextures(1, array, 0)
            val tex = array[0]

            glBindTexture(GL10.GL_TEXTURE_2D, tex)
            glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR.toFloat())

            val bmp = BitmapFactory.decodeResource(renderer.context.resources, textureName)
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0)
            bmp.recycle()

            return tex
        }
    }

    fun draw(gl: GL10) {
        gl.run {
            glEnableClientState(GL10.GL_VERTEX_ARRAY)
            glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)

            glEnable(GL10.GL_TEXTURE_2D)
            glBindTexture(GL10.GL_TEXTURE_2D, texture)
            glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY)
            glTexCoordPointer(2, GL10.GL_FLOAT, 0, texCoordBuffer)

            glEnable(GL10.GL_DEPTH_TEST)
            glDepthFunc(GL10.GL_LESS)

            glDrawElements(GL10.GL_TRIANGLES, totalNumIndices, GL10.GL_UNSIGNED_SHORT, indexBuffer)

            //glDisable(GL10.GL_TEXTURE_2D)
            glDisableClientState(GL10.GL_VERTEX_ARRAY)
            glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY)
        }
    }
/*
    {
        gl.run {
            val time = (System.currentTimeMillis() - startTime) / 1000f
            glTranslatef(0f, 0f, -3f)
            glRotatef(time * 120f, 1f, 0f, 0f)
            glRotatef(time * 50f, 0f, 1f, 0f)

            glEnableClientState(GL10.GL_VERTEX_ARRAY)
            glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)

            glEnable(GL10.GL_DEPTH_TEST)
            glDepthFunc(GL10.GL_LESS)

            glDrawElements(GL10.GL_TRIANGLES, totalNumIndices, GL10.GL_UNSIGNED_SHORT, indexBuffer)

            glDisableClientState(GL10.GL_VERTEX_ARRAY)
            glDisableClientState(GL10.GL_COLOR_ARRAY)
        }
    }
    */
}