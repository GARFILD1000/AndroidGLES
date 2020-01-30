package com.example.phonglighting

import android.opengl.GLES20.*
import android.opengl.GLUtils
import android.opengl.Matrix
import java.nio.Buffer

class Shader(vertexShaderSource: String, fragmentShaderSource: String) {
    private val program: Int = createProgram(vertexShaderSource, fragmentShaderSource)

    fun use() {
        glUseProgram(program)
    }

    fun setFloat(uniform: String, float: Float) {
        use()
        glUniform1f(ul(uniform), float)
    }

    fun setFloat2(uniform: String, float1: Float, float2: Float) {
        use()
        glUniform2f(ul(uniform), float1, float2)
    }

    fun setFloat3(uniform: String, float1: Float, float2: Float, float3: Float) {
        use()
        glUniform3f(ul(uniform), float1, float2, float3)
    }

    fun setFloat4(uniform: String, float1: Float, float2: Float, float3: Float, float4: Float) {
        use()
        glUniform4f(ul(uniform), float1, float2, float3, float4)
    }

    fun setMatrix(uniform: String, matrix: FloatArray) {
        use()
        glUniformMatrix4fv(ul(uniform), 1, false, matrix, 0)
    }

    fun setVertexAttribute(attribute: String, size: Int, type: Int, normalized: Boolean,
                           stride: Int, buffer: Buffer
    ) {
        use()
        val location = glGetAttribLocation(program, attribute)
        glEnableVertexAttribArray(location)
        glVertexAttribPointer(location, size, type, normalized, stride, buffer)
    }

    private fun ul(uniform: String) = glGetUniformLocation(program, uniform)

    private fun createProgram(vertSrc: String, fragSrc: String): Int {
        return glCreateProgram().also { prog ->
            addShader(prog, GL_VERTEX_SHADER, vertSrc)
            addShader(prog, GL_FRAGMENT_SHADER, fragSrc)
            glLinkProgram(prog)
        }
    }

    private fun addShader(program: Int, type: Int, src: String) {
        glCreateShader(type).also { shader ->
            glShaderSource(shader, src)
            glCompileShader(shader)
            glAttachShader(program, shader)
        }
    }
}