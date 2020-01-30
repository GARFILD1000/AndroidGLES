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

uniform sampler2D texture;
varying vec2 uv;

void main(void) {
    vec4 tex_color = texture2D(texture, uv);

    vec4 lightDirection = normalize(lightPosition);
    vec4 diffuse = tex_color * lightDiffuse * max(0.0, dot(normalVarying, lightDirection));
    diffuse = clamp(diffuse, 0.0, 1.0);

    vec4 toViewer = normalize(-positionView);
    vec4 reflectedLightDir = normalize(reflect(-lightDirection, normalVarying));


    vec4 specular = lightSpecular * pow(clamp(dot(toViewer, reflectedLightDir), 0.0, 1.0), shininess);
    vec4 fixed_specular = vec4(specular.r, specular.g, specular.b, 1.0);
    if (fixed_specular.r > 0.5) {
        fixed_specular.r = 0.0;
    }
    if (fixed_specular.g > 0.5) {
        fixed_specular.g = 0.0;
    }
    if (fixed_specular.b > 0.5) {
        fixed_specular.b = 0.0;
    }


    gl_FragColor = lightAmbient + diffuse + fixed_specular;
}