package com.example.flaginfog

import android.graphics.PointF
import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.WindowManager
import android.widget.SeekBar
import com.jmedeisis.bugstick.JoystickListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.*

class MainActivity : AppCompatActivity() {

    private var lastTouchPoint = PointF()
    private lateinit var renderer: Renderer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        initRenderer()
    }


    private fun initRenderer(){
        surface?: return
        surface.setEGLContextClientVersion(2)
        renderer = Renderer(this@MainActivity, fogFactorBar)
        surface.setRenderer(renderer)
        surface.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
        surface.setOnTouchListener { view, motionEvent ->
            when(motionEvent.actionMasked){
                MotionEvent.ACTION_DOWN -> {
                    lastTouchPoint.x = motionEvent.rawX
                    lastTouchPoint.y = motionEvent.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    val dX = motionEvent.rawX - lastTouchPoint.x
                    val dY = motionEvent.rawY - lastTouchPoint.y
                    renderer.camera.rotateView(-dY * 0.1f, -dX * 0.1f)
                    lastTouchPoint.x = motionEvent.rawX
                    lastTouchPoint.y = motionEvent.rawY
                }
            }
            true
        }
        renderer.camera.translateView(0f, -10f, 0f)
//        joystick.setJoystickListener(object: JoystickListener{
//            override fun onDrag(degrees: Float, offset: Float) {
//                Log.d("Joystick","Degrees: ${degrees} Offset: $offset Radius ${joystick.radius}")
//                renderer.camera.moveView(cos(degrees.toRadians()), -sin(degrees.toRadians()))
//            }
//            override fun onDown() {
//
//            }
//            override fun onUp() {
//
//            }
//        })
    }


}

fun Float.toRadians(): Float {
    return (this * PI / 180f).toFloat()
}

fun Double.toRadians(): Double {
    return this * PI / 180.0
}
