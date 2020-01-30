package com.example.phonglighting

import android.content.Context
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import java.nio.charset.Charset
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class Renderer(private val context: Context) : GLSurfaceView.Renderer {
    private lateinit var sphere: Sphere
    private val matrix = FloatArray(16)
    private val modelViewMatrix = FloatArray(16)

    private var touchX = 0f
    private var touchY = 0f

    fun touch(x: Float, y: Float) {
        touchX = x
        touchY = y
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        sphere.shader.setMatrix("mvp", matrix)
        sphere.shader.setMatrix("mv", modelViewMatrix)
//        sphere.shader.setMatrix("normalMatrix", normalMatrix)
        sphere.shader.setFloat4("lightPosition", -10f + 20f * touchX,
            -10f + 20f * (1f - touchY), 14f, 0f)
        sphere.draw()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        val aspect = width.toFloat() / height

        Matrix.setIdentityM(matrix, 0)
        Matrix.perspectiveM(matrix, 0, 70f, aspect, 0.1f, 20f)
        //Matrix.perspectiveM(matrix, 0, -1f, 1f, -aspect, aspect, 0.1f, 20f)
        Matrix.translateM(matrix, 0, 0f, 0f, -3f)

        Matrix.setIdentityM(modelViewMatrix, 0)
        Matrix.translateM(modelViewMatrix, 0, 0f, 0f, -3f)
//        Matrix.invertM(normalMatrix, 0, matrix, 0)
//        Matrix.transposeM(normalMatrix, 0, normalMatrix, 0)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        glEnable(GL_DEPTH_TEST)
        glDepthFunc(GL_LESS)
        glClearColor(0f, 0f, 0f, 1f)

        val shader = Shader(loadRawString(R.raw.sphere_vertex), loadRawString(R.raw.sphere_fragment))
        shader.setFloat4("color", 0.07f, 0.1f, 0.9f, 1f)
        shader.setFloat4("lightPosition", 1f, 1f, 1.5f, 0f)
        shader.setFloat4("lightAmbient", 0.05f, 0.05f, 0.05f, 1f)
        shader.setFloat4("lightDiffuse", 1f, 1f, 1f, 1f)
        shader.setFloat4("lightSpecular", 1f, 1f, 1f, 1f)
        shader.setFloat("shininess", 1f)

        sphere = Sphere(0.7f, 48, shader)
    }

    private fun loadRawString(id: Int): String {
        context.resources.openRawResource(id).use {
            return it.readBytes().toString(Charset.defaultCharset())
        }
    }
}