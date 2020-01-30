package com.example.user.cursovaya

import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin
import kotlin.random.Random

class FireLight {
    val lightMatrix = FloatArray(16)
    val lightPosition = FloatArray(3)
    val lightColor = FloatArray(3)

    fun getProcessedPosition(): FloatArray {
        val processedLightPosition = FloatArray(3)
        processedLightPosition[0] = lightPosition[0] + 0.1f * sin(4.0f * time)
        processedLightPosition[1] = lightPosition[1] + 0.1f * cos(3.0f * time + 1.5f)
        processedLightPosition[2] = lightPosition[2] + 0.05f * cos(2.0f * time + 2.0f)
        return processedLightPosition
    }

    fun getProcessedColor(): FloatArray {
        val processedLightColor = FloatArray(3)
        val rand = Random(startTime).nextFloat() * 0.2f
        processedLightColor[0] = (lightColor[0] + rand * sin(3.0f * time + 3f * rand)).clamp(0f, 1f)
        processedLightColor[1] = (lightColor[1] + rand * sin(3.0f * time + 3f * rand)).clamp(0f, 1f)
        processedLightColor[2] = (lightColor[2] + rand * sin(3.0f * time + 3f * rand)).clamp(0f, 1f)
        return processedLightColor
    }

    private val startTime: Long = System.currentTimeMillis()
    private val time get() = (System.currentTimeMillis() - startTime) * 0.001f

//    fun processLight(){
//    }

}

fun Float.clamp(min: Float, max: Float): Float {
    return max(min, min(max, this))
}