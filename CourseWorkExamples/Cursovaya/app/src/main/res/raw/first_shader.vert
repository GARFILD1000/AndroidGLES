precision highp float;
uniform mat4 light_space_matrix;
attribute vec3 a_vertex;

void main() {
    gl_Position = light_space_matrix * vec4(a_vertex, 1.0);
}