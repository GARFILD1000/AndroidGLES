package com.example.user.cursovaya;

import android.content.Context
import android.opengl.GLES20;

import java.nio.FloatBuffer;
import java.nio.charset.Charset

class Shader {
    private var programHandle = -1

    constructor(context: Context, vertexShaderResId: Int, fragmentShaderResId: Int){
        createProgram(
                loadRawString(context, vertexShaderResId),
                loadRawString(context, fragmentShaderResId)
        )
    }

    constructor(vertexShader: String, fragmentShader: String) {
        createProgram(vertexShader, fragmentShader)
    }

    private fun loadRawString(context: Context, id: Int): String {
        context.resources.openRawResource(id).use {
            return it.readBytes().toString(Charset.defaultCharset())
        }
    }

    fun createProgram(vertexShader: String, fragmentShader: String) {
        val vertexShaderHandle = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER)
        GLES20.glShaderSource(vertexShaderHandle, vertexShader)
        GLES20.glCompileShader(vertexShaderHandle)
        val fragmentShaderHandle = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER)
        GLES20.glShaderSource(fragmentShaderHandle, fragmentShader)
        GLES20.glCompileShader(fragmentShaderHandle)
        programHandle = GLES20.glCreateProgram()
        GLES20.glAttachShader(programHandle, vertexShaderHandle)
        GLES20.glAttachShader(programHandle, fragmentShaderHandle)
        GLES20.glLinkProgram(programHandle)
    }

    fun linkVertexBuffer(vertexBuffer: FloatBuffer) {
        GLES20.glUseProgram(programHandle)
        val aVertexHandle = GLES20.glGetAttribLocation(programHandle, "a_vertex")
        GLES20.glEnableVertexAttribArray(aVertexHandle)
        GLES20.glVertexAttribPointer(aVertexHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer)
    }

    fun linkNormalBuffer(normalBuffer: FloatBuffer) {
        GLES20.glUseProgram(programHandle)
        val aNormalHandle = GLES20.glGetAttribLocation(programHandle, "a_normal")
        GLES20.glEnableVertexAttribArray(aNormalHandle)
        GLES20.glVertexAttribPointer(aNormalHandle, 3, GLES20.GL_FLOAT, false, 0, normalBuffer)
    }

    fun linkModelViewProjectionMatrix(modelViewProjectionMatrix: FloatArray) {
        GLES20.glUseProgram(programHandle)
        val uMVPmatrixHandle = GLES20.glGetUniformLocation(programHandle, "u_model_view_projection_matrix")
        GLES20.glUniformMatrix4fv(uMVPmatrixHandle, 1, false, modelViewProjectionMatrix, 0)
    }

    fun linkLightModelViewProjectionMatrix(modelViewProjectionMatrix: FloatArray) {
        GLES20.glUseProgram(programHandle)
        val lightSpaceMatrixHandle = GLES20.glGetUniformLocation(programHandle, "light_space_matrix")
        GLES20.glUniformMatrix4fv(lightSpaceMatrixHandle, 1, false, modelViewProjectionMatrix, 0)
    }

    fun linkCameraPosition(cameraPosition: FloatArray) {
        GLES20.glUseProgram(programHandle)
        val uCameraHandle = GLES20.glGetUniformLocation(programHandle, "u_camera")
        GLES20.glUniform3f(uCameraHandle, cameraPosition[0], cameraPosition[1], cameraPosition[2])
    }

    fun linkLightPosition(lightPosition: FloatArray) {
        GLES20.glUseProgram(programHandle)
        val uLightSourceHandle = GLES20.glGetUniformLocation(programHandle, "u_light_position")
        GLES20.glUniform3f(uLightSourceHandle, lightPosition[0], lightPosition[1], lightPosition[2])
    }

    fun linkLightColor(lightColor: FloatArray) {
        GLES20.glUseProgram(programHandle)
        val uLightColorHandle = GLES20.glGetUniformLocation(programHandle, "u_light_color")
        GLES20.glUniform3f(uLightColorHandle, lightColor[0], lightColor[1], lightColor[2])
    }

    fun linkTime(time: Float) {
        GLES20.glUseProgram(programHandle)
        val uTimeHandle = GLES20.glGetUniformLocation(programHandle, "u_time")
        GLES20.glUniform1f(uTimeHandle, time)
    }

    fun linkTexture(texture: IntArray) {
        GLES20.glUseProgram(programHandle);
        val uTextureHandle = GLES20.glGetUniformLocation(programHandle, "u_texture")
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[0])
        GLES20.glUniform1i(uTextureHandle, 0)
    }

    fun linkDepthMap(shadow: IntArray) {
        GLES20.glUseProgram(programHandle)
        val shadowMapHandle = GLES20.glGetUniformLocation(programHandle, "u_s_texture")
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, shadow[0])
        GLES20.glUniform1i(shadowMapHandle, 0)
    }

    fun linkTextureBuffer(textureBuffer: FloatBuffer) {
        GLES20.glUseProgram(programHandle)
        val uTextureCoordinateHandle = GLES20.glGetAttribLocation(programHandle, "a_tex_coordinates")
        GLES20.glEnableVertexAttribArray(uTextureCoordinateHandle)
        GLES20.glVertexAttribPointer(uTextureCoordinateHandle, 2, GLES20.GL_FLOAT, false, 0, textureBuffer);
    }

    fun useProgram() {
        GLES20.glUseProgram(programHandle)
    }
}
