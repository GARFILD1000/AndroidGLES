uniform mat4 u_model_view_projection_matrix;
uniform mat4 light_space_matrix;
uniform float u_time;

attribute vec2 a_tex_coordinates;
attribute vec3 a_vertex;
attribute vec3 a_normal;

varying vec2 v_tex_coordinates;
varying vec3 v_vertex;
varying vec3 v_normal;
varying vec4 v_light_vertex;


void main() {
    v_vertex = a_vertex;
    vec3 n_normal = normalize(a_normal);
    v_normal = n_normal;
    v_tex_coordinates = a_tex_coordinates;
    v_light_vertex = light_space_matrix * vec4(a_vertex, 1.0);
    gl_Position = u_model_view_projection_matrix * vec4(a_vertex, 1.0);
}