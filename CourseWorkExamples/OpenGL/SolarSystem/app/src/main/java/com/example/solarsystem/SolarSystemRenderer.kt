package com.example.solarsystem

import android.content.Context
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class SolarSystemRenderer(val context: Context) : GLSurfaceView.Renderer {
    private val startTime = System.currentTimeMillis()

    private val time get() = (System.currentTimeMillis() - startTime) * 0.001f

    private lateinit var sun: Celestial
    private lateinit var earth: Celestial
    private lateinit var moon: Celestial

    override fun onDrawFrame(gl: GL10?) {
        if (gl == null) return

        gl.run {
            glLoadIdentity()
            glClearColor(0f, 0f, 0f, 1f)
            glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
            glTranslatef(0f, 0f, -4f)

            glPushMatrix()
            glRotatef(time * 30f, 0f, 1f, 0f)
            sun.draw(gl)

            glPopMatrix()
            glRotatef(time * 40f, 0f, 1f, -0.2f)
            glTranslatef(0.7f, 0f, 0f)
            glRotatef(time * 100f, 0f, 1f, 0.1f)
            earth.draw(gl)

            glTranslatef(0.2f, 0f, 0f)
            moon.draw(gl)
        }
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        if (gl == null) return
        gl.run {
            glMatrixMode(GL10.GL_PROJECTION)
            val aspect = height.toFloat() / width
            glLoadIdentity()
            glOrthof(-1f, 1f, -aspect, aspect, 0.1f, 20f)
            glMatrixMode(GL10.GL_MODELVIEW)
        }
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        if (gl == null) return

        sun = Celestial(gl, 0.25f, R.drawable.sun, this)
        earth = Celestial(gl, 0.1f, R.drawable.earth, this)
        moon = Celestial(gl, 0.035f, R.drawable.moon, this)

        gl.glEnable(GL10.GL_TEXTURE_2D)
    }
}