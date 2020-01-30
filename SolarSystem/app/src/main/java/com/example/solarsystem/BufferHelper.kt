package com.example.solarsystem

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer

object BufferHelper {
    fun directFloat(vararg floats: Float): FloatBuffer =
        ByteBuffer.allocateDirect(floats.size * 4).apply {
            order(ByteOrder.nativeOrder())
        }.asFloatBuffer().apply {
            put(floats)
            position(0)
        }

    fun directShort(vararg shorts: Short): ShortBuffer =
        ByteBuffer.allocateDirect(shorts.size * 2).apply {
            order(ByteOrder.nativeOrder())
        }.asShortBuffer().apply {
            put(shorts)
            position(0)
        }

    fun directByte(vararg bytes: Byte): ByteBuffer =
        ByteBuffer.allocateDirect(bytes.size).apply {
            order(ByteOrder.nativeOrder())
            put(bytes)
            position(0)
        }
}