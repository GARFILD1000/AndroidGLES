#ifdef GL_ES
precision highp float;
#endif

varying vec4 positionVarying;
 varying vec4 positionView;
varying vec4 normalVarying;
varying vec2 texcoordVarying;

uniform sampler2D mainTexture;
uniform vec3 lightDirection;
uniform vec3 lightColor;

uniform float fogFactor;
uniform vec3 fogColor;

vec4 applyFog(in vec4 color, in float dist) {
	float x = (dist * fogFactor);
	float t = 1.0 - exp(-x * x);
	return vec4(mix(color.rgb, fogColor, t), color.a);
}

void main(void) {
    vec4 color = texture2D(mainTexture, texcoordVarying);

    vec4 diffuse = color * max(0.0, 0.2 + 0.8 * dot(normalVarying.xyz, normalize(-lightDirection)));
    diffuse.xyz *= lightColor;
    diffuse = clamp(diffuse, 0.0, 1.0);

    float dist = length(positionView);
    vec4 finalColor = applyFog(diffuse, dist);

    gl_FragColor = finalColor;
}