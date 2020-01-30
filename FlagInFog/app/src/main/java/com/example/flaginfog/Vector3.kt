package com.example.flaginfog

data class Vector3(val x: Float, val y: Float, val z: Float) {
    companion object {
        val zero = Vector3(0f, 0f, 0f)
    }

    operator fun plus(v: Vector3) = Vector3(x + v.x, y + v.y, z + v.z)
    operator fun minus(v: Vector3) = Vector3(x - v.x, y - v.y, z - v.z)
    operator fun unaryMinus() = Vector3(-x, -y, -z)
    operator fun times(f: Float) = Vector3(f * x, f * y, f * z)
}
