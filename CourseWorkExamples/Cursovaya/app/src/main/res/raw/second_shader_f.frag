precision highp float;
uniform vec3 u_camera;
uniform vec3 u_light_position;
uniform sampler2D u_texture;
uniform sampler2D u_s_texture;
varying vec3 v_vertex;
varying vec3 v_normal;
varying vec4 v_light_vertex;
varying vec2 v_tex_coordinates;


void main() {
    vec3 n_normal = normalize(v_normal);
    vec3 light_vector = normalize(u_light_position - v_vertex);
    vec3 look_vector = normalize(u_camera - v_vertex);
    float ambient = 0.3;
    float k_diffuse = 1.0;
    float k_specular = 0.0;
    float diffuse = k_diffuse * max(dot(n_normal, light_vector), 0.0);
    vec3 reflect_vector = reflect(-light_vector, n_normal);
    float specular = k_specular * pow(max(dot(look_vector, reflect_vector), 0.0), 40.0);
    vec4 one = vec4(1.0, 1.0, 1.0, 1.0);
    vec4 depth = v_light_vertex / v_light_vertex.w;
    float depth_light = texture2D(u_s_texture, depth.st).z;
    float shadow = depth.z < depth_light ? 1.0 : 0.0;
    vec4 light = (ambient + shadow * (diffuse + specular)) * one;
    gl_FragColor = texture2D(u_texture, v_tex_coordinates) * light;
}