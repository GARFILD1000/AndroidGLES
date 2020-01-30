package com.example.solarsystem

import android.content.Context
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class SolarSystemRenderer(val context: Context) : GLSurfaceView.Renderer {
    private val startTime = System.currentTimeMillis()
    var maxScale = 3f
    var minScale = 0.005f
    var scale = 1f
    set(value){
        if (value in minScale*distancePerUnit..maxScale*distancePerUnit){
            field = value
        }
    }
    var distancePerUnit = 1f

    var rotation = FloatArray(3).apply{
        set(0, 0f); set(1, 0f); set(2, 0f)
    }
    var speed = 1f
    private val time get() = (System.currentTimeMillis() - startTime) * 0.001f

    private var planets = HashMap<Int, Planet>()
    private lateinit var sun: Celestial
    private lateinit var space: Celestial

    override fun onDrawFrame(gl: GL10?) {
        if (gl == null) return

        gl.run {
            glLoadIdentity()
            glClearColor(0f, 0f, 0f, 1f)
            glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
            glTranslatef(0f, 0f, -4f)
            glScalef(scale, scale, scale)
            gl.glRotatef(rotation[0], 1f, 0f, 0f)
            gl.glRotatef(rotation[1], 0f, 1f, 0f)
            gl.glRotatef(rotation[2], 0f, 0f, 1f)


            glPushMatrix()
            glRotatef(time * 10f * speed, 0f, 1f, 0f)
            sun.draw(gl)
            glPopMatrix()

            for (planetMap in planets){
                val planet = planetMap.value
                //glPushMatrix()
                glPushMatrix()
                glRotatef(time * (1f / planet.yearDuration) * 50f * speed, 0f, 1f, 0.1f)
                glTranslatef(planet.orbitRadius, 0f, 0f)
                glRotatef(time * (1f / planet.dayDuration) * speed, 0f, 1f, 0.1f)
                planet.draw(gl)
                glPopMatrix()
                //glPopMatrix()
            }

//            space.draw(gl)
        }
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        if (gl == null) return
        gl.run {
            glMatrixMode(GL10.GL_PROJECTION)
            val aspect = height.toFloat() / width
            glLoadIdentity()
            glOrthof(-1f, 1f, -aspect, aspect, 0f, 200f)
            glMatrixMode(GL10.GL_MODELVIEW)
        }
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        if (gl == null) return

        planets.put(MERCURY, Planet(gl, 0.038f, 1.2f * distancePerUnit, 0.241f, 59f, R.drawable.mercury, this))

        planets.put(VENUS, Planet(gl, 0.095f, 2.1f * distancePerUnit, 0.6164f, 243f, R.drawable.venus, this))

        planets.put(EARTH, Planet(gl, 0.1f, 3f * distancePerUnit, 1f, 1f, R.drawable.earth, this))

        planets.put(MARS, Planet(gl, 0.053f, 4.5f * distancePerUnit, 1.8821f, 1.0343f, R.drawable.mars, this))

        planets.put(JUPITER, Planet(gl, 1.12f, 15.6f * distancePerUnit, 12f, 0.4125f, R.drawable.jupiter, this))

        planets.put(SATURN, Planet(gl, 0.94f, 28.8f * distancePerUnit, 30f, 0.425f, R.drawable.saturn, this))

        planets.put(URANUS, Planet(gl, 0.4f, 57.6f * distancePerUnit, 84f, 0.7187f, R.drawable.uranus, this))

        planets.put(NEPTUNE, Planet(gl, 0.39f, 90f * distancePerUnit, 165f, 0.6716f, R.drawable.neptune, this))

        planets.put(PLUTON, Planet(gl, 0.018f, 118f * distancePerUnit, 248f, 0.6716f, R.drawable.pluton, this))

        sun = Celestial(gl, 1f, R.drawable.sun, this)

        space = Celestial(gl, 150f, R.drawable.space_stars, this)

        gl.glEnable(GL10.GL_TEXTURE_2D)
    }

    companion object{
        const val MERCURY = 0
        const val VENUS = 1
        const val EARTH = 2
        const val MARS = 3
        const val JUPITER = 4
        const val SATURN = 5
        const val URANUS = 6
        const val NEPTUNE = 7
        const val PLUTON = 8
    }
}