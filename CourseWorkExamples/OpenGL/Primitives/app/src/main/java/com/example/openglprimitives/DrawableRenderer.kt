package com.example.openglprimitives

import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class DrawableRenderer() : GLSurfaceView.Renderer {
    var drawable: Drawable? = null

    override fun onDrawFrame(gl: GL10?) {
        if (gl == null) return

        gl.run {
            glLoadIdentity()
            glClearColor(1f, 1f, 1f, 1f)
            glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
        }
        drawable?.draw(gl)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        if (gl == null) return

        val aspect = height.toFloat() / width
        gl.run {
            glMatrixMode(GL10.GL_PROJECTION)
            glLoadIdentity()
            glOrthof(-1f, 1f, -aspect, aspect, 0.1f, 20f)
            glMatrixMode(GL10.GL_MODELVIEW)
        }
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        if (gl == null) return
    }
}