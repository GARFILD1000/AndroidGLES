package com.example.solarsystem

import kotlin.math.cos
import kotlin.math.sin

class SphereGenerator {
    data class SphereData (
        val vertices: FloatArray,
        val texCoords: FloatArray,
        val indices: ShortArray
    )

    companion object {
        fun generate(radius: Float, n: Int): SphereData {
            val totalNumVerts = 2 + (n - 1) * (n * 2 + 1)
            val numEquatorVerts = n * 2

            val vertices = mutableListOf<Float>()
            val texCoords = mutableListOf<Float>()
            val indices = mutableListOf<Int>()

            fun addVertex(x: Float, y: Float, z: Float) = vertices.apply { add(x); add(y); add(z) }
            fun addTexCoord(u: Float, v: Float) = texCoords.apply { add(u); add(v) }

            // Vertices and texCoords
            // Top vertex
            addVertex(0f, radius, 0f)
            addTexCoord(0f, 1f)
            // Body vertices
            for (i in 1 until n) {
                val v = 1f - i.toFloat() / n
                val latitude = Math.PI * v
                for (j in 0..numEquatorVerts) {
                    val u = j.toFloat() / numEquatorVerts
                    val longitude = 2 * Math.PI * u
                    val r = radius * sin(latitude)
                    val y = radius * cos(latitude)
                    val x = r * sin(longitude)
                    val z = r * cos(longitude)
                    addVertex(x.toFloat(), y.toFloat(), z.toFloat())
                    addTexCoord(u, v)
                }
            }
            // Bottom vertex
            addVertex(0f, -radius, 0f)
            addTexCoord(0f, 0f)

            // Indices
            // Top cap
            for (j in 1..numEquatorVerts) {
                indices.apply { add(0); add(j); add(1 + j) }
            }
            // Body indices
            for (i in 0 until n - 2) {
                for (j in 0 until numEquatorVerts) {
                    val w = (numEquatorVerts + 1)
                    val i1 = 1 + w * i + j
                    val i2 = 1 + w * i + j + 1
                    val i3 = 1 + w * (i + 1) + j
                    val i4 = 1 + w * (i + 1) + j + 1

                    indices.apply { add(i1); add(i2); add(i3); add(i2); add(i3); add(i4) }
                }
            }
            // Bottom cap
            val lastIndex = totalNumVerts - 1
            val offset = lastIndex - numEquatorVerts - 1
            for (j in 1..numEquatorVerts) {
                indices.apply { add(offset + j); add(offset + 1 + j); add(lastIndex) }
            }

            return SphereData(
                vertices.toFloatArray(),
                texCoords.toFloatArray(),
                indices.map(Int::toShort).toShortArray()
            )
        }
    }
}