package com.example.openglprimitives

import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val renderer = DrawableRenderer()
        initSurfaceAndRenderer(renderer)

        initRadioButton(rbSquare, renderer, Square())
        initRadioButton(rbCube, renderer, Cube())
        initRadioButton(rbSphere, renderer, Sphere(1f, 48))

        rbSquare.isChecked = true
    }

    private fun initSurfaceAndRenderer(renderer: GLSurfaceView.Renderer) {
        val surface = GLSurfaceView(this)
        surface.setRenderer(renderer)
        surface.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
        gfxParent.addView(surface)
    }

    private fun initRadioButton(rb: RadioButton, renderer: DrawableRenderer, attachedDrawable: Drawable) {
        rb.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                renderer.drawable = attachedDrawable
            }
        }
    }
}
