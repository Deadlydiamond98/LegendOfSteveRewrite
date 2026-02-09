#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0; // main texture
uniform sampler2D Sampler3; // normals texture
uniform sampler2D Sampler4; // gradient texture

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform vec3 LegendOfSteveIridescenceItemOffset;

in float vertexDistance;
in vec4 vertexColor;
in vec3 vertexPos;
in vec3 vertexNormal;
in vec4 lightMapColor;
in vec4 overlayColor;
in vec2 texCoord0;
in vec4 normal;

out vec4 fragColor;

#moj_import <legend_of_steve:iridescence.glsl>

void main() {
    vec4 colorTex = texture(Sampler0, texCoord0);
    if (colorTex.a <= 0.1) discard;

    vec4 shineColor = shineCol(Sampler3, Sampler4, LegendOfSteveIridescenceItemOffset);

    vec4 color = vec4(
    shineColor.rgb * shineColor.a + colorTex.rgb * (1 - shineColor.a), 1) * vertexColor * ColorModulator;
    color.rgb = mix(overlayColor.rgb, color.rgb, overlayColor.a);
    color *= lightMapColor;

    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}