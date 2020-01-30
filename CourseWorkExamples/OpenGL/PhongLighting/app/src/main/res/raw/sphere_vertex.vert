#ifdef GL_ES
precision highp float;
#endif

attribute vec4 position;
attribute vec4 normal;

uniform mat4 mvp;
uniform mat4 mv;
//uniform mat4 normalMatrix;

varying vec4 positionVarying;
varying vec4 positionView;
varying vec4 normalVarying;

void main() {
    gl_Position = mvp * position;
    normalVarying = normal;
    positionVarying = mvp * position;
    positionView = mv * position;
}