package com.example.modelviewer

import android.opengl.GLES20

class Axis(axisLength: Double){
    var verticles = arrayOf(
        -axisLength, 0.0,0.0,   axisLength,0.0,0.0,  //x
        axisLength, 0.0,0.0,   axisLength,0.0,0.0,

        0.0,-axisLength,0.0,   0.0,axisLength,0.0,  //y
        0.0, axisLength,0.0,   0.0,axisLength,0.0,

        0.0,0.0,-axisLength,   0.0,0.0,axisLength,  //z
        0.0,0.0, axisLength,   0.0,0.0,axisLength
    )
    var shader: Shader? = null

    fun draw(){
        
    }

}