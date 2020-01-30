attribute vec4 position;
attribute vec2 texcoord;
varying vec2 uv;

void main() {
    gl_Position = position;
    uv = texcoord;
}