package com.example.user.cursovaya;

import android.opengl.GLES20;

import java.nio.FloatBuffer;

public class Shader {
    private int program_handle;

    Shader(String vertex_shader, String fragment_shader) {
        createProgram(vertex_shader, fragment_shader);
    }

    void createProgram(String vertex_shader, String fragment_shader) {
        int vertex_shader_handle = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(vertex_shader_handle, vertex_shader);
        GLES20.glCompileShader(vertex_shader_handle);
        int fragment_shader_handle = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(fragment_shader_handle, fragment_shader);
        GLES20.glCompileShader(fragment_shader_handle);
        program_handle = GLES20.glCreateProgram();
        GLES20.glAttachShader(program_handle, vertex_shader_handle);
        GLES20.glAttachShader(program_handle, fragment_shader_handle);
        GLES20.glLinkProgram(program_handle);
    }

    void linkVertexBuffer(FloatBuffer vertexBuffer) {
        GLES20.glUseProgram(program_handle);
        int a_vertex_handle = GLES20.glGetAttribLocation(program_handle, "a_vertex");
        GLES20.glEnableVertexAttribArray(a_vertex_handle);
        GLES20.glVertexAttribPointer(a_vertex_handle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);
    }

    void linkNormalBuffer(FloatBuffer normalBuffer) {
        GLES20.glUseProgram(program_handle);
        int a_normal_handle = GLES20.glGetAttribLocation(program_handle, "a_normal");
        GLES20.glEnableVertexAttribArray(a_normal_handle);
        GLES20.glVertexAttribPointer(a_normal_handle, 3, GLES20.GL_FLOAT, false, 0, normalBuffer);
    }

    void linkModelViewProjectionMatrix(float[] modelViewProjectionMatrix) {
        GLES20.glUseProgram(program_handle);
        int u_model_view_projection_matrix_handle = GLES20.glGetUniformLocation(program_handle, "u_model_view_projection_matrix");
        GLES20.glUniformMatrix4fv(u_model_view_projection_matrix_handle, 1, false, modelViewProjectionMatrix, 0);
    }

    void linkLightModelViewProjectionMatrix(float[] modelViewProjectionMatrix) {
        GLES20.glUseProgram(program_handle);
        int light_space_matrix_handle = GLES20.glGetUniformLocation(program_handle, "light_space_matrix");
        GLES20.glUniformMatrix4fv(light_space_matrix_handle, 1, false, modelViewProjectionMatrix, 0);
    }

    void linkCamera(float xCamera, float yCamera, float zCamera) {
        GLES20.glUseProgram(program_handle);
        int u_camera_handle = GLES20.glGetUniformLocation(program_handle, "u_camera");
        GLES20.glUniform3f(u_camera_handle, xCamera, yCamera, zCamera);
    }

    void linkLightSource(float xLightPosition, float yLightPosition, float zLightPosition) {
        GLES20.glUseProgram(program_handle);
        int u_light_source_handle = GLES20.glGetUniformLocation(program_handle, "u_light_position");
        GLES20.glUniform3f(u_light_source_handle, xLightPosition, yLightPosition, zLightPosition);
    }

    void linkTexture(int[] texture) {
        GLES20.glUseProgram(program_handle);
        int u_texture_Handle = GLES20.glGetUniformLocation(program_handle, "u_texture");
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[0]);
        GLES20.glUniform1i(u_texture_Handle, 0);
    }

    void linkDepthMap(int[] shadow) {
        GLES20.glUseProgram(program_handle);
        int shadow_map_Handle = GLES20.glGetUniformLocation(program_handle, "u_s_texture");
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, shadow[0]);
        GLES20.glUniform1i(shadow_map_Handle, 0);
    }

    void linkTextureBuffer(FloatBuffer texture_buffer) {
        GLES20.glUseProgram(program_handle);
        int u_texture_coordinate_handle = GLES20.glGetAttribLocation(program_handle, "a_tex_coordinates");
        GLES20.glEnableVertexAttribArray(u_texture_coordinate_handle);
        GLES20.glVertexAttribPointer(u_texture_coordinate_handle, 2, GLES20.GL_FLOAT, false, 0, texture_buffer);
    }

    void use_program() {
        GLES20.glUseProgram(program_handle);
    }

}
