#ifdef GL_ES
precision highp float;
#endif

varying vec2 uv;
uniform sampler2D texture;

uniform float time;
uniform vec2 resolution;
uniform float alpha;
uniform vec2 rippleCenter;
uniform float rippleStartTime;

void main(void) {
    vec2 pos = uv;
    float ratio = resolution.x / resolution.y;
    pos.x *= ratio;

    vec2 rc = rippleCenter / resolution;
    rc.y = 1.0 - rc.y;
    rc.x *= ratio;
    float d = distance(pos, rc);
    const float tSpeed = 4.0;
    const float dSpeed = 20.0;

    //float amplitude = exp(-20.0 * (time - rippleStartTime - d)); // it's beautiul
    float t = 2.0 * (time - rippleStartTime);
    float amplitude = exp(-2.2 * (t - d));
    amplitude *= 1.5 * step(amplitude, 1.0);

    float sine = amplitude * sin(2.0 * 3.14 * 2.0 * (t - d));

    float offset = 0.01 * sine;

    vec4 color = texture2D(texture, uv + offset) + sine * 0.11 + 0.05;
    gl_FragColor = vec4(color.r, color.g, color.b, alpha);
}