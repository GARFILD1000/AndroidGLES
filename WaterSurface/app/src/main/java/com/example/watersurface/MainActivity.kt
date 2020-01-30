package com.example.watersurface

import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val surface = GLSurfaceView(this).apply {
            setEGLContextClientVersion(2)
            val renderer = Renderer(this@MainActivity)
            setRenderer(renderer)
            renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
            setOnTouchListener { _, event ->
                renderer.touch(event.x, event.y)

                true
            }
        }
        setContentView(surface)
    }
}
