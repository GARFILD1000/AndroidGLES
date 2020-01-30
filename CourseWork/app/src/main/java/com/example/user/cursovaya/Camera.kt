package com.example.user.cursovaya

import android.opengl.Matrix
import android.util.Log
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class Camera {
    var viewRotation = FloatArray(3).apply{
        fillWith(0f)
    }

    var position = FloatArray(4)
    get(){
        field.fillWith(1f)
        Matrix.multiplyMV(field, 0, viewMatrix, 0, field, 0)
        field.negate()
//        Log.d("CameraPosition", "x=${field[0]} y=${field[1]} z=${field[2]}")
        return field
    }

    val viewMatrix = FloatArray(16).also {
        Matrix.setIdentityM(it, 0)
        Matrix.setLookAtM(it, 0, 0f, 5f, 6f,0f, 1f, 0f, 0f, 1f, 0f)
    }

    fun rotateView(x: Float, y: Float){
        viewRotation[0] += (cos(viewRotation[1] * PI / 180) * x).toFloat()
        viewRotation[1] += y
        viewRotation[2] += (sin(viewRotation[1] * PI / 180) * x).toFloat()
        Matrix.rotateM(viewMatrix, 0, (cos(viewRotation[1].toRadians()) * x), 1f,0f,0f)
        Matrix.rotateM(viewMatrix, 0, y, 0f,1f,0f)
        Matrix.rotateM(viewMatrix, 0, (sin(viewRotation[1].toRadians()) * x), 0f,0f,1f)
    }
}

fun FloatArray.fillWith(value: Float) {
    this.forEachIndexed{ idx, _ ->
        set(idx, value)
    }
}

fun FloatArray.negate() {
    this.forEachIndexed{ idx, value ->
        set(idx, -value)
    }
}