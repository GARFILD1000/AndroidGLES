package com.example.phonglighting

import android.content.Context
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import java.io.FileInputStream
import java.nio.charset.Charset
import java.util.*
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class Renderer(private val context: Context) : GLSurfaceView.Renderer {
    companion object {
        const val MODEL_FILENAME = "RictuCauldron.obj"
        const val TEXTURE_FILENAME = "RictuCauldron.png"
    }

    private var objects: LinkedList<Object> = LinkedList()

    private var lightPoint: Array<Float> = arrayOf(10f , 10f, -30f)

    private val matrix = FloatArray(16)
    private val modelViewMatrix = FloatArray(16)
    var shader: Shader? = null

    private var touchX = 0f
    private var touchY = 0f
    var sceneScale = 1f
    var rotation = FloatArray(3).apply{
        set(0, 0f); set(1, 0f); set(2, 0f)
    }

    var texture: Int = 0
    var uTextureUnitLocation: Int = 0


    fun touch(x: Float, y: Float) {
        touchX = x
        touchY = y
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        val matrixCopy = matrix.clone()
        val modelViewMatrixCopy = modelViewMatrix.clone()
        Matrix.scaleM(matrixCopy, 0, sceneScale, sceneScale, sceneScale)
        Matrix.rotateM(matrixCopy, 0, rotation[0], 1f, 0f, 0f)
        Matrix.rotateM(matrixCopy, 0, rotation[1], 0f, 1f, 0f)
        Matrix.rotateM(matrixCopy, 0, rotation[2], 0f, 0f, 1f)



        shader?.apply {
            setMatrix("mvp", matrixCopy)
            setMatrix("mv", modelViewMatrixCopy)
            //        sphere.shader.setMatrix("normalMatrix", normalMatrix)
            setFloat4("lightPosition", lightPoint[0], lightPoint[1], lightPoint[2], 0f)
        }
        objects.forEach {
            it.draw()
        }
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        val aspect = width.toFloat() / height
        Matrix.setIdentityM(matrix, 0)
        Matrix.perspectiveM(matrix, 0, 70f, aspect, 0.1f, 20f)
        //Matrix.perspectiveM(matrix, 0, -1f, 1f, -aspect, aspect, 0.1f, 20f)
        Matrix.translateM(matrix, 0, 0f, 0f, -3f)
        Matrix.setIdentityM(modelViewMatrix, 0)
        Matrix.translateM(modelViewMatrix, 0, 0f, 0f, -3f)
//        Matrix.invertM(normalMatrix, 0, matrix, 0)
//        Matrix.transposeM(normalMatrix, 0, normalMatrix, 0)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        glEnable(GL_DEPTH_TEST)
        glDepthFunc(GL_LESS)
        glClearColor(0f, 0f, 0f, 1f)



        shader = Shader(loadRawString(R.raw.sphere_vertex), loadRawString(R.raw.sphere_fragment)).apply {
            setFloat4("lightPosition", 1f, 1f, 1.5f, 0f)
            setFloat4("lightAmbient", 0.05f, 0.05f, 0.05f, 1f)
            setFloat4("lightDiffuse", 1f, 1f, 1f, 1f)
            setFloat4("lightSpecular", 1f, 1f, 1f, 1f)
            setFloat("shininess", 1f)
        }

        val modelInputStream = App.getAppContext().assets.open(MODEL_FILENAME)
        val textureInputStream = App.getAppContext().assets.open(TEXTURE_FILENAME)
        val model = Model(OBJParser.parse(modelInputStream))

        model.shader = shader
        model.texture = Texture2D(textureInputStream)

//        val sphere = Sphere(0.7f, 48)
//        sphere.shader = shader
//        sphere.texture = Texture2D(textureInputStream)
//        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.earth)
        //sphere.initTexture(bitmap)
//        bitmap.recycle()

        objects.add(model)
//        objects.add(sphere)
    }


    private fun loadRawString(id: Int): String {
        context.resources.openRawResource(id).use {
            return it.readBytes().toString(Charset.defaultCharset())
        }
    }
}