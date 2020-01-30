package com.example.watersurface

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.GLUtils
import java.nio.charset.Charset
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class Renderer(private val context: Context) : GLSurfaceView.Renderer {
    private lateinit var bottom: Plane
    private lateinit var water: Plane

//    private var w = 0f
//    private var h = 0f

    private var rippleCenterX = 0f
    private var rippleCenterY = 0f
    private var rippleStartTime = -99999f


    private val startTime: Long = System.currentTimeMillis()
    private val time get() = (System.currentTimeMillis() - startTime) * 0.001f

    fun touch(x: Float, y: Float) {
        rippleCenterX = x
        rippleCenterY = y
        rippleStartTime = time
//        water.shader.setFloat2("rippleCenter", x, y)
//        water.shader.setFloat("rippleStartTime", time)
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)

        bottom.draw()
        water.shader.setFloat2("rippleCenter", rippleCenterX, rippleCenterY)
        water.shader.setFloat("rippleStartTime", rippleStartTime)
        water.shader.setFloat("time", time)
        water.draw()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
//        w = width.toFloat()
//        h = height.toFloat()
        water.shader.setFloat2("resolution", width.toFloat(), height.toFloat())
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

        val bottomTexture = Texture2D(R.drawable.bottom, context)
        val waterTexture = Texture2D(R.drawable.water, context)

        val bottomShader = Shader(loadRawString(R.raw.bottom_vertex), loadRawString(R.raw.bottom_fragment))

        val waterShader = Shader(loadRawString(R.raw.water_vertex), loadRawString(R.raw.water_fragment))
        waterShader.setFloat("rippleStartTime", -1000000f)
        waterShader.setFloat("alpha", 0.7f)

        bottom = Plane(bottomTexture, bottomShader)
        water = Plane(waterTexture, waterShader)

        glClearColor(0f, 0f, 0f, 1f)
    }

    private fun loadRawString(id: Int): String {
        context.resources.openRawResource(id).use {
            return it.readBytes().toString(Charset.defaultCharset())
        }
    }
}