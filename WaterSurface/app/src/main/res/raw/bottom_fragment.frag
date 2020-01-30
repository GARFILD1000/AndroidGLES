#ifdef GL_ES
precision highp float;
#endif

varying vec2 uv;  // position received from vertex shader
uniform sampler2D texture;


void main(void) {
    vec4 color = texture2D(texture, uv);

    gl_FragColor = vec4(color.r, color.g, color.b, 1);
}