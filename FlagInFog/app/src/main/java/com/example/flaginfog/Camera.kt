package com.example.flaginfog

import android.opengl.Matrix
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class Camera {
    var viewRotation = FloatArray(3).apply{
        set(0, 0f); set(1, 0f); set(2, 0f)
    }

    private val viewMatrix = FloatArray(16).also {
        Matrix.setIdentityM(it, 0)
    }

    private val rotationMatrix = FloatArray(16).also {
        Matrix.setIdentityM(it, 0)
    }

    private val translationMatrix = FloatArray(16).also {
        Matrix.setIdentityM(it, 0)
    }

    fun rotateView(x: Float, y: Float){
        viewRotation[0] += (cos(viewRotation[1] * PI / 180) * x).toFloat()
        viewRotation[1] += y
        viewRotation[2] += (sin(viewRotation[1] * PI / 180) * x).toFloat()
        Matrix.rotateM(rotationMatrix, 0, (cos(viewRotation[1].toRadians()) * x), 1f,0f,0f)
        Matrix.rotateM(rotationMatrix, 0, y, 0f,1f,0f)
        Matrix.rotateM(rotationMatrix, 0, (sin(viewRotation[1].toRadians()) * x), 0f,0f,1f)


    }

    fun translateView(x: Float, y:Float, z: Float) {
        Matrix.translateM(translationMatrix, 0, x, y, z)
    }

    fun getViewMatrix(): FloatArray{
        Matrix.multiplyMM(viewMatrix, 0, rotationMatrix, 0, translationMatrix, 0)
        return viewMatrix
    }
}