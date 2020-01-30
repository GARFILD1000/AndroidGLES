package com.example.opnegl_lab1

import android.opengl.GLES10
import android.opengl.GLES10.glOrthof
import android.opengl.GLES10.glTranslatef
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.math.abs
import kotlin.math.sin


class GLRenderer() : GLSurfaceView.Renderer{
    private var lastTime = System.currentTimeMillis()

    private val cube: Cube = Cube()
    private val square: Square = Square()
    private val sphere: Sphere = Sphere(1f, 15)
    var deltaAngle: Float = 10f
    var angle = 0f
    var anglePerSecond = 180f

    override fun onDrawFrame(gl: GL10?) {
        gl?:return
        deltaAngle = (System.currentTimeMillis() - lastTime).toFloat() / 1000f * anglePerSecond
        lastTime = System.currentTimeMillis()

        angle = (angle + deltaAngle)

        gl.run {
            glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)

            glLoadIdentity()
            glTranslatef(0f, 0f, -3f) /// -1 <= z <= 1
            //glRotatef(angle, 0f, 1f, 0f)
        }

        gl.glPushMatrix()
        glTranslatef(0f, -2f, 0f)
        square.rotate(0f, angle/10f, angle * 4)
        square.scale(0.5f)
        square.draw(gl)
        gl.glPopMatrix()



        gl.glPushMatrix()
        glTranslatef(0f, -0f, 0f)
        cube.rotate(0f, angle, 0f)
        cube.scale(0.5f)
        cube.draw(gl)
        gl.glPopMatrix()

        gl.glPushMatrix()
        glTranslatef(0f, 2f, 0f)
        sphere.rotate(0f, 0f, angle * 2)
        sphere.scale(0.5f)
        sphere.draw(gl)
        gl.glPopMatrix()

        //gl.glPushMatrix()
        //glTranslatef(0f, 3f, 0f)
        //gl.glPopMatrix()
        //gl?.glRotatef(angle, 0f, 1f, 0f)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        gl?.run {
            glViewport(0,0, width, height)
            glMatrixMode(GL10.GL_PROJECTION)
            glLoadIdentity()
            val r = height.toFloat() / width.toFloat()
            glOrthof(-1f,1f, -r,r,0.01f, 15f)
            glScalef(0.5f, 0.5f, 0.5f)
            glMatrixMode(GL10.GL_MODELVIEW)
        }
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        gl?.glClearColor(0f,0f,0f,0f)
    }

}