#version 120
uniform mat4 u_model_view_projection_matrix;

attribute vec3 a_vertex;

varying float v_depth;

void main() {
    vec4 position = u_model_view_projection_matrix * vec4(a_vertex, 1.0);
    float z_buf = position.z / position.w;
    v_depth = 0.5 + 0.5 * zBuf;
    gl_Position = position;
}
