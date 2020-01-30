package com.example.flaginfog

import android.content.Context
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.Log
import android.widget.SeekBar
import java.nio.charset.Charset
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.math.absoluteValue

class Renderer(private val context: Context,
               private val fogFactorBar: SeekBar
) : GLSurfaceView.Renderer {
    private var backgroundColor = Vector3(0.2f, 0.2f, 0.2f)
    private var lightDirection = Vector3(-0.5f, -0.3f, -0.2f)
    private var lightColor = Vector3(0.9f, 0.9f, 0.9f)
    private var fogColor = backgroundColor

    var camera = Camera()

    val projMatrix = FloatArray(16).also {
        Matrix.setIdentityM(it, 0)
    }

    private val startTime: Long = System.currentTimeMillis()
    private val time get() = (System.currentTimeMillis() - startTime) * 0.001f

    private lateinit var hill: Mesh
    private lateinit var staff: Mesh
    private lateinit var flag: Mesh

    fun setCommonShaderUniforms(shader: Shader) {
        shader.setFloat3("lightDirection", lightDirection)
        shader.setFloat3("lightColor", lightColor)
        shader.setFloat("time", time, true)
        shader.setFloat3("fogColor", fogColor, true)
        val fogFactor = 2f * fogFactorBar.progress.toFloat() / fogFactorBar.max
        shader.setFloat("fogFactor", fogFactor, true)
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        hill.draw(camera)
        staff.draw(camera)
        flag.draw(camera)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        val aspect = width.toFloat() / height
        Matrix.perspectiveM(projMatrix, 0, 70f, aspect, 0.1f, 1000f)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        glEnable(GL_DEPTH_TEST)

        setupScene()

//        Matrix.setLookAtM(viewMatrix, 0,
//            0f, 1.1f, 1f,
//            0.6f, 0.6f, -1.9f,
//            0f, 1f, 0f)

        glClearColor(backgroundColor.x, backgroundColor.y, backgroundColor.z, 1f)
    }

    private fun setupScene() {
        val groundTexture = Texture2D(R.drawable.scene_texture, context)
        val woodTexture = Texture2D(R.drawable.metal, context)
        val flagTexture = Texture2D(R.drawable.flag, context)

        val standardShader = shaderFromRaw(R.raw.standard_vert, R.raw.standard_frag)
        val flagShader = shaderFromRaw(R.raw.flag_vert, R.raw.flag_frag)

        hill = Mesh(this, ObjectParser(context, R.raw.scene), groundTexture, standardShader)
        staff = Mesh(this, ObjectParser(context, R.raw.staff), woodTexture, standardShader)
        staff.position = Vector3(-0.054f, 0.8f, -0.036f)
        flag = Mesh(this, ObjectParser(context, R.raw.flag), flagTexture, flagShader)
        flag.position = Vector3(-0.02f, 1.01f, -0.04f)
    }

    private fun shaderFromRaw(vertexId: Int, fragmentId: Int)
        = Shader(loadRawString(vertexId), loadRawString(fragmentId))

    private fun loadRawString(id: Int): String {
        context.resources.openRawResource(id).use {
            return it.readBytes().toString(Charset.defaultCharset())
        }
    }


}