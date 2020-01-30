precision highp float;

varying float v_depth;

void main() {
    gl_FragColor = vec4(vDepth, 0.0, 0.0, 1.0);
}
