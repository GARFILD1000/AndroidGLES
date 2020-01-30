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
uniform float time;

void main() {
	vec4 pos = position;

	pos.z += texcoord.x * 0.15 * sin(5.0 * time + 1.3 * texcoord.x + 0.7 * texcoord.y);
	pos.y += texcoord.x * 0.02 * sin(3.0 * time + 1.3 * texcoord.x);

    gl_Position = mvp * pos;
    normalVarying = normalMatrix * normal;
    positionVarying = mvp * pos;
    positionView = mv * pos;
    texcoordVarying = texcoord;
}