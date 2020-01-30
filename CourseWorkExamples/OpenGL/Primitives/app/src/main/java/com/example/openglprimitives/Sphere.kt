package com.example.openglprimitives

import java.nio.FloatBuffer
import java.nio.ShortBuffer
import javax.microedition.khronos.opengles.GL10
import kotlin.math.cos
import kotlin.math.sin

class Sphere(radius: Float, n: Int) : Drawable {
    private val scale = 0.618f

    private val vertexBuffer: FloatBuffer
    private val colorBuffer: FloatBuffer
    private val indexBuffer: ShortBuffer
    private val totalNumIndices: Int

    init {
        val vertices = mutableListOf<Float>()
        val colors = mutableListOf<Float>()
        val indices = mutableListOf<Int>()

        val totalNumVerts = 2 + (n - 1) * n * 2
        val numEquatorVerts = n * 2

        fun addVertex(x: Float, y: Float, z: Float) {
            vertices.apply { add(x); add(y); add(z) }

            fun r(c: Float) = 0.5f + 0.5f * c
            colors.apply { add(r(x)); add(r(y)); add(r(z)); add(1f) }
        }

        // Vertices
        // Top vertex
        addVertex(0f, radius, 0f)
        // Body vertices
        for (i in 1 until n) {
            val latitude = Math.PI * i / n
            for (j in 0 until numEquatorVerts) {
                val longitude = 2 * Math.PI * j / numEquatorVerts
                val r = radius * sin(latitude)
                val y = radius * cos(latitude)
                val x = r * sin(longitude)
                val z = r * cos(longitude)
                addVertex(x.toFloat(), y.toFloat(), z.toFloat())
            }
        }
        // Bottom vertex
        addVertex(0f, -radius, 0f)

        // Indices
        // Top cap
        for (j in 1..numEquatorVerts) {
            indices.apply { add(0); add(j); add(1 + (j % numEquatorVerts)) }
        }
        // Body indices
        for (i in 0 until n - 2) {
            for (j in 0 until numEquatorVerts) {
                val i1 = 1 + numEquatorVerts * i + j
                val i2 = 1 + numEquatorVerts * i + ((j + 1) % numEquatorVerts)
                val i3 = 1 + numEquatorVerts * (i + 1) + j
                val i4 = 1 + numEquatorVerts * (i + 1) + ((j + 1) % numEquatorVerts)

                indices.apply { add(i1); add(i2); add(i3); add(i2); add(i3); add(i4) }
            }
        }
        // Bottom cap
        val lastIndex = totalNumVerts - 1
        val offset = lastIndex - numEquatorVerts - 1
        for (j in 1..numEquatorVerts) {
            indices.apply { add(offset + j); add(offset + 1 + (j % numEquatorVerts)); add(lastIndex) }
        }

        vertexBuffer = BufferHelper.directFloat(*vertices.toFloatArray())
        colorBuffer = BufferHelper.directFloat(*colors.toFloatArray())
        indexBuffer = BufferHelper.directShort(*indices.map(Int::toShort).toShortArray())

        totalNumIndices = indices.size
    }

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

            glEnable(GL10.GL_DEPTH_TEST)
            glDepthFunc(GL10.GL_LESS)

            glDrawElements(GL10.GL_TRIANGLES, totalNumIndices, GL10.GL_UNSIGNED_SHORT, indexBuffer)

            glDisableClientState(GL10.GL_VERTEX_ARRAY)
            glDisableClientState(GL10.GL_COLOR_ARRAY)
        }
    }
}