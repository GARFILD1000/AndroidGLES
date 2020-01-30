package com.example.phonglighting

import android.annotation.SuppressLint
import android.graphics.PointF
import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.abs
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    lateinit var renderer: Renderer
    private var lastTouchPoint = PointF()

    //    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        surfaceView.setEGLContextClientVersion(2)
        renderer = Renderer(this)
        surfaceView.setRenderer(renderer)
        surfaceView.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
        surfaceView.setOnTouchListener { _, event ->
            renderer.touch(event.x / surfaceView.width, event.y / surfaceView.height)
            true
        }
        zoomBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
                seekBar?: return
                if (progress > 0) renderer.sceneScale =
                    1f - seekBar.max/progress.toFloat()
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        surfaceView.setOnTouchListener { view, motionEvent ->
            when(motionEvent.actionMasked){
                MotionEvent.ACTION_DOWN -> {
                    lastTouchPoint.x = motionEvent.rawX
                    lastTouchPoint.y = motionEvent.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    val dX = motionEvent.rawX - lastTouchPoint.x
                    val dY = motionEvent.rawY - lastTouchPoint.y
                    if (motionEvent.pointerCount == 1) {
                        renderer.rotation[1] += dX
                        renderer.rotation[0] += dY
                    }
                    lastTouchPoint.x = motionEvent.rawX
                    lastTouchPoint.y = motionEvent.rawY
                }
            }
            true
        }
    }
}
