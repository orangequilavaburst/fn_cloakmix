#version 150

#moj_import <matrix.glsl>

uniform sampler2D Sampler0;

uniform float GameTime;
uniform int AlteredSightLayers;

in vec4 texProj0;

const mat4 SCALE_TRANSLATE = mat4(
    1.0, 0.0, 0.0, 0.0,
    0.0, 1.0, 0.0, 0.0,
    0.0, 0.0, 1.0, 0.0,
    0.0, 0.0, 0.0, 1.0
);

const vec3[] COLORS = vec3[](
    vec3(0.0, (159.0/255.0), (183.0/255.0)),
    vec3((53.0/255.0), (131.0/255.0), (149.0/255.0)),
    vec3((105/255.0), (103.0/255.0), (115.0/255.0)),
    vec3((180.0/255.0), (159.0/255.0), (109.0/255.0)),
    vec3((254.0/255.0), (215.0/255.0), (102.0/255.0))
);

mat4 basetranslate = mat4(
        1.0, 0.0, 0.0, 0.0,
        0.0, 1.0, 0.0, 0.0,
        0.0, 0.0, 1.0, 0.0,
        0.0, 0.0, 0.0, 1.0
    );


mat4 altered_sight_layer(float layer) {
    mat4 translate = mat4(
        1.0, 0.0, 0.0, 0.0,
        0.0, 1.0, 0.0, 0.0,
        0.0, 0.0, 1.0, 0.0,
        0.0, 0.0, 0.0, 1.0
    );


    mat2 rotate = mat2_rotate_z(radians(0.0));

    mat2 scale = mat2(1.0);

    return mat4(scale * rotate) * translate * SCALE_TRANSLATE;
}

out vec4 fragColor;

void main() {

    vec3 color = textureProj(Sampler0, texProj0 * basetranslate).rgb * 1.0;
    for (int i = 0; i < AlteredSightLayers; i++){

        float freq = 2.0 * 3.1415;
        float t = freq/AlteredSightLayers * i + GameTime*500;
        float s = sin(t)/2 + 0.5;
        color -= textureProj(Sampler0, texProj0 * altered_sight_layer(i)).rgb * COLORS[i]*s*(1.0/AlteredSightLayers);

    }

    fragColor = vec4(color, 1.0);

}