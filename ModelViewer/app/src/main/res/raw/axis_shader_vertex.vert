attribute vec3 a_Position;
attribute vec3 aColor;

uniform mat4 u_Pmatrix;
uniform mat4 u_Mmatrix;
uniform mat4 u_Vmatrix;

varying vec3 vColor;

void main() {
    vColor = aColor;
    gl_Position = u_Pmatrix * u_Vmatrix * u_Mmatrix * vec4(a_Position,1.0);
}