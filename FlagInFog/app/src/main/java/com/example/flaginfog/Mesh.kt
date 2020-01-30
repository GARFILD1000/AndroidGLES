package com.example.flaginfog

import android.opengl.GLES20.*
import android.opengl.Matrix

class Mesh(
    private val renderer: Renderer,
    private val objLoader: ObjectParser,
    val texture: Texture2D,
    val shader: Shader
) {
    var position: Vector3 = Vector3.zero
        set(value) {
            field = value
            val delta = value - position
            Matrix.translateM(matrix, 0, delta.x, delta.y, delta.z)
        }

    private val matrix = FloatArray(16).also { Matrix.setIdentityM(it, 0) }
    private val mv = FloatArray(16)
    private val mvp = FloatArray(16)
    private val normalMatrix = FloatArray(16)
    private val tempMatrix = FloatArray(16)

    private val vertexBuffer = BufferHelper.directFloat(*objLoader.positions)
    private val normalBuffer = BufferHelper.directFloat(*objLoader.normals)
    private val texcoordBuffer = BufferHelper.directFloat(*objLoader.texcoords)

    fun draw(camera: Camera) {
        shader.use()

        renderer.setCommonShaderUniforms(shader)

        shader.setVertexAttribute("position", 3, GL_FLOAT, false, 0, vertexBuffer)
        shader.setVertexAttribute("normal", 3, GL_FLOAT, true, 0, normalBuffer)
        shader.setVertexAttribute("texcoord", 2, GL_FLOAT, false, 0, texcoordBuffer)

        shader.setTexture("mainTexture", 0, texture)

        Matrix.multiplyMM(mv, 0, camera.getViewMatrix(), 0, matrix, 0)
        shader.setMatrix("mv", mv)
        Matrix.multiplyMM(mvp, 0, renderer.projMatrix, 0, mv, 0)
        shader.setMatrix("mvp", mvp)

        Matrix.invertM(tempMatrix, 0, mv, 0)
        Matrix.transposeM(normalMatrix, 0, tempMatrix, 0)
        shader.setMatrix("normalMatrix", normalMatrix)

        glDrawArrays(GL_TRIANGLES, 0, objLoader.positions.size / 3)
    }
}