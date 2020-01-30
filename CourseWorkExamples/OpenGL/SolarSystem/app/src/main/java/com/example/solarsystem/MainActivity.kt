package com.example.solarsystem

import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val surface = GLSurfaceView(this)

        surface.setRenderer(SolarSystemRenderer(this))
        surface.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
        setContentView(surface)
    }
}
