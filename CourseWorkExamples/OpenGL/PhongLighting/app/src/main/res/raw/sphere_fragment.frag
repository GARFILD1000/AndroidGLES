#ifdef GL_ES
precision highp float;
#endif

varying vec4 normalVarying;
varying vec4 positionView;
varying vec4 positionVarying;

uniform vec4 color;
uniform vec4 lightPosition;
uniform vec4 lightAmbient;
uniform vec4 lightDiffuse;
uniform vec4 lightSpecular;
uniform float shininess;

void main(void) {
    vec4 lightDirection = normalize(lightPosition);
    vec4 diffuse = color * lightDiffuse * max(0.0, dot(normalVarying, lightDirection));
    diffuse = clamp(diffuse, 0.0, 1.0);

    vec4 toViewer = normalize(-positionView);
    vec4 reflectedLightDir = normalize(-reflect(lightDirection, normalVarying));
    
    vec4 specular = 5.0 * lightSpecular * pow(max(0.0, dot(toViewer, reflectedLightDir)), shininess);
    specular = clamp(specular, 0.0, 1.0);

    gl_FragColor = lightAmbient + diffuse + specular;
}