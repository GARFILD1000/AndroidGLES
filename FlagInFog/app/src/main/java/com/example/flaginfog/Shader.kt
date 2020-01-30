package com.example.flaginfog

import android.opengl.GLES20.*
import java.lang.RuntimeException
import java.nio.Buffer

class Shader(vertexShaderSource: String, fragmentShaderSource: String) {
    private val program: Int = createProgram(vertexShaderSource, fragmentShaderSource)

    fun use() {
        glUseProgram(program)
    }

    fun setFloat(uniform: String, f: Float, dontThrowWhenNotFound: Boolean = false) {
        use()
        withUniform(uniform, dontThrowWhenNotFound) {
            glUniform1f(it, f)
        } 
    }

    fun setFloat2(uniform: String, f1: Float, f2: Float, dontThrowWhenNotFound: Boolean = false) {
        use()
        withUniform(uniform, dontThrowWhenNotFound) {
            glUniform2f(it, f1, f2)
        }
    }

    fun setFloat3(uniform: String, f1: Float, f2: Float, f3: Float, dontThrowWhenNotFound: Boolean = false) {
        use()
        withUniform(uniform, dontThrowWhenNotFound) {
            glUniform3f(it, f1, f2, f3)
        }
    }

    fun setFloat3(uniform: String, vector3: Vector3, dontThrowWhenNotFound: Boolean = false) {
        setFloat3(uniform, vector3.x, vector3.y, vector3.z, dontThrowWhenNotFound)
    }

    fun setMatrix(uniform: String, matrix: FloatArray, dontThrowWhenNotFound: Boolean = false) {
        withUniform(uniform, dontThrowWhenNotFound) {
            glUniformMatrix4fv(it, 1, false, matrix, 0)
        }
    }

    fun setTexture(uniform: String, number: Int, texture: Texture2D, dontThrowWhenNotFound: Boolean = false) {
        use()
        withUniform(uniform, dontThrowWhenNotFound) {
            texture.use(GL_TEXTURE0 + number)
            glUniform1i(it, number)
        }
    }

    fun setVertexAttribute(attribute: String, size: Int, type: Int, normalized: Boolean,
                           stride: Int, buffer: Buffer
    ) {
        use()
        val location = glGetAttribLocation(program, attribute)
        glEnableVertexAttribArray(location)
        glVertexAttribPointer(location, size, type, normalized, stride, buffer)
    }

    private inline fun withUniform(uniform: String, dontThrowWhenNotFound: Boolean,
                                   action: (uniformLocation: Int) -> Unit) {
        val location = glGetUniformLocation(program, uniform)
        if (location != -1) {
            action(location)
        } else if (!dontThrowWhenNotFound) {
            throw IllegalArgumentException("Uniform '$uniform' not found")
        }
    } 

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

            val tmp = IntArray(1)
            glGetShaderiv(shader, GL_COMPILE_STATUS, tmp, 0)
            val success = tmp[0]
            if (success == GL_FALSE) {
                val message = glGetShaderInfoLog(shader)
                glDeleteShader(shader)
                throw RuntimeException("Failed to compile shader:\n$message")
            }

            glAttachShader(program, shader)
        }
    }
}