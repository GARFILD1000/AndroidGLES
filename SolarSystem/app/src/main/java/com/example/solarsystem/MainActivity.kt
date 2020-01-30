package com.example.solarsystem

import android.graphics.PointF
import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.WindowManager
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Math.pow
import kotlin.math.abs
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    private var solarSystemRenderer = SolarSystemRenderer(this)
    private lateinit var surface: GLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        init()
    }

    private var lastTouchPoint = PointF()
    private val THRESHOLD = 1f
    private fun init(){
        val scaleListener = object: ScaleGestureDetector.SimpleOnScaleGestureListener(){
            override fun onScale(detector: ScaleGestureDetector?): Boolean {
                detector?:return super.onScale(detector)

                solarSystemRenderer.scale *= sqrt(detector.scaleFactor)
                Log.d(
                    "System scale",
                    "${solarSystemRenderer.scale} factor ${detector.scaleFactor}"
                )
                return super.onScale(detector)
            }
        }
        val scaleDetector = ScaleGestureDetector(this, scaleListener)

        surface = surfaceView
        surface.setRenderer(solarSystemRenderer)
        surface.setOnTouchListener { view, motionEvent ->
            when(motionEvent.actionMasked){
                MotionEvent.ACTION_DOWN -> {
                    lastTouchPoint.x = motionEvent.rawX
                    lastTouchPoint.y = motionEvent.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    val dX = motionEvent.rawX - lastTouchPoint.x
                    val dY = motionEvent.rawY - lastTouchPoint.y
                    if (abs(dX) > THRESHOLD || abs(dY) > THRESHOLD) {
                        scaleDetector.onTouchEvent(motionEvent)
                    }
                    if (motionEvent.pointerCount == 1) {
                        solarSystemRenderer.rotation[1] += dX
                        solarSystemRenderer.rotation[0] += dY
                    }
                    lastTouchPoint.x = motionEvent.rawX
                    lastTouchPoint.y = motionEvent.rawY
                }
            }
            true
        }
        zoomBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
                if (progress > 0) solarSystemRenderer.scale = 1f/ sqrt(progress.toDouble()).toFloat()
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
        speedBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (progress > 0) solarSystemRenderer.speed = progress.toFloat() / 10f
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }


}
