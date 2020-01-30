package com.example.user.cursovaya;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.util.*

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

class Renderer : GLSurfaceView.Renderer {
    private var objects = LinkedList<Object>()
    val camera = Camera()
    private var context: Context

    private val depthMapFbo = IntArray(1)
    private val depthMap = IntArray(1)
    private var screenWidth = 0
    private var screenHeight = 0

    companion object {
        private val modelMatrix = FloatArray(16)
        //        private val viewMatrix = FloatArray(16)
        private val modelViewMatrix = FloatArray(16)
        private val projectionMatrix = FloatArray(16)
        private val modelViewProjectionMatrix = FloatArray(16)
        private val fireLight = FireLight()
        private val renderDistance = 40f
        private val startTime: Long = System.currentTimeMillis()
        private val time get() = (System.currentTimeMillis() - startTime) * 0.001f
    }

    private var secondShader: Shader? = null

    constructor(context: Context) {
        this.context = context

        try {
            val scene = Object(context, R.raw.scene_obj, R.raw.scene)
            val table = Object(context, R.raw.table_wood_rectangle_obj, R.raw.table_wood_rectangle)
            val knight = Object(context, R.raw.knight_obj, R.raw.knight)
            knight.translation[0] = -1.5f
            knight.translation[2] = -5.7f
            val board1 = Object(context, R.raw.black_board_obj, R.raw.black_board1)
            board1.translation[0] = 8f
            board1.translation[2] = -3.4f
            board1.rotation[1] = 315f
            val board2 = Object(context, R.raw.black_board_obj, R.raw.black_board2)
            board2.translation[0] = -8f
            board2.translation[2] = -3.4f
            board2.rotation[1] = 45f
            val chest = Object(context, R.raw.chest_obj, R.raw.chest_wood)
            chest.translation[0] = 5.8f
            chest.translation[2] = 5.6f
            chest.rotation[1] = 270f
            val beans = Object(context, R.raw.beans_obj, R.raw.beans)
            beans.translation[0] = 1.4f
            beans.translation[1] = 2.4f
            val candle = Object(context, R.raw.candle_obj, R.raw.candle)
            candle.translation[0] = -1f
            candle.translation[1] = 2.3f

            objects.addAll(listOf(
                    scene,
                    candle,
                    table,
                    knight,
                    board1,
                    board2,
                    chest,
                    beans
                    ))

            Matrix.setIdentityM(fireLight.lightMatrix, 0)
            Matrix.translateM(fireLight.lightMatrix, 0, candle.translation[0], candle.translation[1] + 0.5f, candle.translation[2])
            fireLight.lightPosition[0] = candle.translation[0]
            fireLight.lightPosition[1] = candle.translation[1] + 0.2f
            fireLight.lightPosition[2] = candle.translation[2]
            fireLight.lightColor[0] = 1f
            fireLight.lightColor[1] = 0.9f
            fireLight.lightColor[2] = 0.9f
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }


    private fun createDepthMapTexture() {
        GLES20.glGenTextures(1, depthMap, 0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, depthMap[0])
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE)
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0,
                GLES20.GL_DEPTH_COMPONENT, 2048, 2048, 0,
                GLES20.GL_DEPTH_COMPONENT, GLES20.GL_UNSIGNED_INT, null)
        GLES20.glGenFramebuffers(1, depthMapFbo, 0)
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, depthMapFbo[0])
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_DEPTH_ATTACHMENT,
                GLES20.GL_TEXTURE_2D, depthMap[0], 0)
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0)
    }

//    private fun initializeViewMatrix() {
//        Matrix.setIdentityM(modelMatrix, 0)
//        Matrix.setLookAtM(camera.getViewMatrix(), 0, 0f, 4f, 4f, 0f, 0f, 0f, 0f, 1f, 0f)
//        Matrix.multiplyMM(modelViewMatrix, 0, viewMatrix, 0, modelMatrix, 0)
//    }

    private fun initializeProjectionMatrix(width: Int, height: Int) {
        val ratio = width / height.toFloat()
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, 1f, 20f)
        Matrix.multiplyMM(modelViewProjectionMatrix, 0, projectionMatrix, 0, modelViewMatrix, 0)
    }

    private fun initializeMvpMatrix(obj: Object) {
//        if (mode == 1) {
//            Matrix.setIdentityM(modelMatrix, 0);
////            Matrix.setLookAtM(viewMatrix, 0, 1.3f, 4f, 0.5f,0f, 0f, 0f, 0f, 1f, 0f)
//            val ratio = screenWidth / screenHeight.toFloat()
//            Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, 1f, renderDistance)
//            Matrix.multiplyMM(modelViewMatrix, 0, camera.viewMatrix, 0, modelMatrix, 0)
//            Matrix.multiplyMM(modelViewProjectionMatrix, 0, projectionMatrix, 0, modelViewMatrix, 0)
//        }
//        if (mode == 2) {
        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.translateM(modelMatrix, 0, obj.translation[0], obj.translation[1], obj.translation[2])
        Matrix.rotateM(modelMatrix, 0, obj.rotation[0], 1f, 0f, 0f)
        Matrix.rotateM(modelMatrix, 0, obj.rotation[1], 0f, 1f, 0f)
        Matrix.rotateM(modelMatrix, 0, obj.rotation[2], 0f, 0f, 1f)
        Matrix.scaleM(modelMatrix, 0, obj.scale[0], obj.scale[1], obj.scale[2])
        val ratio = screenWidth / screenHeight.toFloat()
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, 1f, renderDistance)
        Matrix.multiplyMM(modelViewMatrix, 0, camera.viewMatrix, 0, modelMatrix, 0)
        Matrix.multiplyMM(modelViewProjectionMatrix, 0, projectionMatrix, 0, modelViewMatrix, 0)
    }

    private fun renderScene() {
        createDepthMapTexture()
        for (obj in objects) {
            secondShader?.linkVertexBuffer(obj.vertexBuffer)
            secondShader?.linkNormalBuffer(obj.normalBuffer)
            secondShader?.linkDepthMap(depthMap)
            secondShader?.linkTexture(obj.texture)
            secondShader?.linkTextureBuffer(obj.texcoordBuffer)
            initializeMvpMatrix(obj)
            secondShader?.linkLightModelViewProjectionMatrix(modelViewProjectionMatrix)
            secondShader?.linkModelViewProjectionMatrix(modelViewProjectionMatrix)
            secondShader?.linkCameraPosition(camera.position)
            secondShader?.linkLightPosition(fireLight.getProcessedPosition())
            secondShader?.linkLightColor(fireLight.getProcessedColor())
            secondShader?.linkTime(time)
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, obj.texture[0])
            GLES20.glActiveTexture(GLES20.GL_TEXTURE1)
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, depthMap[0])
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, obj.vertexCount)
        }
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        //        createDepthMapTexture();
//        initializeViewMatrix();
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)
        GLES20.glEnable(GLES20.GL_TEXTURE_2D)
        GLES20.glEnable(GLES20.GL_CULL_FACE)

        secondShader = Shader(context, R.raw.vertex_shader, R.raw.fragment_shader)

        for (obj in objects) {
            obj.loadTexture()
        }
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        screenWidth = width
        screenHeight = height
        gl?.glViewport(0, 0, width, height)
        initializeProjectionMatrix(width, height)
    }


    override fun onDrawFrame(gl: GL10?) {
        try {
            GLES20.glCullFace(GLES20.GL_BACK)
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0)
            GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT or GLES20.GL_COLOR_BUFFER_BIT)
            GLES20.glViewport(0, 0, screenWidth, screenHeight)
            renderScene()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}
