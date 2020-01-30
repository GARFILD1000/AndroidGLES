package com.example.user.cursovaya;

import android.Manifest
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.PointF
import android.opengl.GLSurfaceView;
import android.os.PersistableBundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.PI

class MainActivity: AppCompatActivity() {
    companion object{
        val PERMISSIONS_REQUEST_CODE = 1
    }

    private var lastTouchPoint = PointF()
    private lateinit var renderer: Renderer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_main)

        renderer = Renderer(this@MainActivity)
        surface.setEGLContextClientVersion(2)
        surface.setRenderer(renderer)
        surface.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY)
        surface.setOnTouchListener { view, motionEvent ->
            when(motionEvent.actionMasked){
                MotionEvent.ACTION_DOWN -> {
                    lastTouchPoint.x = motionEvent.rawX
                    lastTouchPoint.y = motionEvent.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    val dX = motionEvent.rawX - lastTouchPoint.x
                    val dY = motionEvent.rawY - lastTouchPoint.y
                    renderer.camera.rotateView(dY * 0.1f, dX * 0.1f)
                    lastTouchPoint.x = motionEvent.rawX
                    lastTouchPoint.y = motionEvent.rawY
                }
            }
            true
        }
    }


//    private fun initRenderer(){
//        surface?: return
//        surface.setEGLContextClientVersion(2)
//        renderer = Renderer(this@MainActivity)
//        surface.setRenderer(renderer)
//        surface.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY

//        renderer.camera.translateView(0f, -10f, 0f)
////        joystick.setJoystickListener(object: JoystickListener{
////            override fun onDrag(degrees: Float, offset: Float) {
////                Log.d("Joystick","Degrees: ${degrees} Offset: $offset Radius ${joystick.radius}")
////                renderer.camera.moveView(cos(degrees.toRadians()), -sin(degrees.toRadians()))
////            }
////            override fun onDown() {
////
////            }
////            override fun onUp() {
////
////            }
////        })
//    }


    private fun checkAllNeededPermissions() {
        val application = (applicationContext as App)
        val neededPermissions = arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        )

        val allPermissionsGranted = application.checkPermissions(this, *neededPermissions)
        if (!allPermissionsGranted) {
            application.requestPermissions(this, PERMISSIONS_REQUEST_CODE, *neededPermissions)
        }
    }
}


fun Float.toRadians(): Float {
    return (this * PI / 180f).toFloat()
}

fun Double.toRadians(): Double {
    return this * PI / 180.0
}
