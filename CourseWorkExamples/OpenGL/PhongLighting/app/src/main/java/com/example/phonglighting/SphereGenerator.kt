package com.example.phonglighting

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class SphereGenerator {
    data class SphereData (
        val vertices: FloatArray,
        val normals: FloatArray,
        val indices: ShortArray
    )

    companion object {
        fun generate(radius: Float, n: Int): SphereData {
            val numCircles = (n - 1)
            val numCircleVerts = n * 2
            val totalNumVerts = 2 + numCircles * numCircleVerts

            val vertices = mutableListOf<Float>()
            val normals = mutableListOf<Float>()
            val indices = mutableListOf<Int>()

            fun addVertex(x: Float, y: Float, z: Float) {
                vertices.apply { add(x); add(y); add(z) }
                val l = sqrt(x * x + y * y + z * z)
                normals.apply { add(x / l); add(y / l); add(z / l) }
            }

            // Top vertex
            addVertex(0f, -radius, 0f)
            // Body vertices
            for (i in 1..numCircles) {
                val v = 1f - i.toFloat() / n
                val latitude = Math.PI * v
                for (j in 0 until numCircleVerts) {
                    val u = j.toFloat() / numCircleVerts
                    val longitude = 2 * Math.PI * u
                    val r = radius * sin(latitude)
                    val y = radius * cos(latitude)
                    val x = r * sin(longitude)
                    val z = r * cos(longitude)
                    addVertex(x.toFloat(), y.toFloat(), z.toFloat())
                }
            }
            // Bottom vertex
            addVertex(0f, radius, 0f)

            // Indices
            // Top cap
            for (j in 0 until numCircleVerts) {
                indices.apply { add(0); add(1 + j); add(1 + ((j + 1) % numCircleVerts)) }
            }
            // Body indices
            for (i in 0 until numCircles - 1) {
                for (j in 0 until numCircleVerts) {
                    val i1 = 1 + numCircleVerts * i + j
                    val i2 = 1 + numCircleVerts * i + ((j + 1) % numCircleVerts)
                    val i3 = 1 + numCircleVerts * (i + 1) + j
                    val i4 = 1 + numCircleVerts * (i + 1) + ((j + 1) % numCircleVerts)

                    indices.apply { add(i1); add(i2); add(i3); add(i2); add(i3); add(i4) }
                }
            }
            // Bottom cap
            val lastIndex = totalNumVerts - 1
            val offset = lastIndex - numCircleVerts
            for (j in 0 until numCircleVerts) {
                indices.apply { add(offset + j); add(offset + ((j + 1) % numCircleVerts)); add(lastIndex) }
            }

            return SphereData(
                vertices.toFloatArray(),
                normals.toFloatArray(),
                indices.map(Int::toShort).toShortArray()
            )
        }
    }
}