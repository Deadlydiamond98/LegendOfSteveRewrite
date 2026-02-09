#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0; // main texture
uniform sampler2D Sampler1; // normals texture
uniform sampler2D Sampler3; // gradient texture

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec4 vertexColor;
in vec3 vertexPos;
in vec3 vertexNormal;
in vec2 texCoord0;

out vec4 fragColor;

#moj_import <legend_of_steve:iridescence.glsl>

void main() {
    vec4 colorTex = texture(Sampler0, texCoord0);
    if (colorTex.a <= 0.1) discard;

    vec4 shineColor = shineCol(Sampler1, Sampler3, vec3(0, 0, 0));

    vec4 color = vec4(shineColor.rgb * shineColor.a + colorTex.rgb * (1 - shineColor.a), 1) * vertexColor * ColorModulator;

    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}