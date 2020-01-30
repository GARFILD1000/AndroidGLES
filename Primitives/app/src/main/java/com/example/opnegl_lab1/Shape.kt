package com.example.opnegl_lab1

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10

abstract class Shape {
    protected var rotation = FloatArray(3).apply {
        set(0, 0f); set(1, 0f); set(2, 0f)
    }

    protected var scale = FloatArray(3).apply {
        set(0, 1f); set(1, 1f); set(2, 1f)
    }


    fun initBuffer(array: FloatArray): FloatBuffer {
        val buffer : FloatBuffer = ByteBuffer
            .allocateDirect(array.size * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
        buffer.put(array)
        buffer.position(0)
        return buffer
    }


    fun rotate(rotationX: Float, rotationY: Float, rotationZ: Float){
        rotation.apply{
            set(0, rotationX); set(1, rotationY); set(0, rotationZ)
        }
    }

    fun scale(scaleX: Float, scaleY: Float, scaleZ: Float){
        scale.apply{
            set(0, scaleX); set(1, scaleY); set(2, scaleZ)
        }
    }

    fun scale(scaleAll: Float){
        scale.apply{
            set(0, scaleAll); set(1, scaleAll); set(2, scaleAll)
        }
    }

    open fun draw(gl: GL10?){
        gl?:return

        gl.glRotatef(rotation[0], 1f, 0f, 0f)
        gl.glRotatef(rotation[1], 0f, 1f, 0f)
        gl.glRotatef(rotation[2], 0f, 0f, 1f)

        gl.glScalef(scale[0], scale[1], scale[2])
    }
}