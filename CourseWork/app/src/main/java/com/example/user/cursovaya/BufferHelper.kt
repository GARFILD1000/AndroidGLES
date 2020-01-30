package com.example.user.cursovaya

import java.nio.*

class BufferHelper {
    companion object {
        fun directFloat(vararg floats: Float): FloatBuffer =
                ByteBuffer.allocateDirect(floats.size * 4).apply {
                    order(ByteOrder.nativeOrder())
                }.asFloatBuffer().apply {
                    put(floats)
                    position(0)
                }

        fun directInt(vararg ints: Int): IntBuffer =
                ByteBuffer.allocateDirect(ints.size * Int.SIZE_BYTES).apply {
                    order(ByteOrder.nativeOrder())
                }.asIntBuffer().apply {
                    put(ints)
                    position(0)
                }

        fun directShort(vararg shorts: Short): ShortBuffer =
                ByteBuffer.allocateDirect(shorts.size * Short.SIZE_BYTES).apply {
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
}