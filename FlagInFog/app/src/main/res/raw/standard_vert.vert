#ifdef GL_ES
precision highp float;
#endif

attribute vec4 position;
attribute vec4 normal;
attribute vec2 texcoord;

varying vec4 positionVarying;
varying vec4 positionView;
varying vec4 normalVarying;
varying vec2 texcoordVarying;

uniform mat4 mvp;
uniform mat4 mv;
uniform mat4 normalMatrix;

void main() {
    gl_Position = mvp * position;
    normalVarying = normalMatrix * normal;
    positionVarying = mvp * position;
    positionView = mv * position;
    texcoordVarying = texcoord;
}