#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform float GameTime;

in float vertexDistance;
in vec4 vertexColor;
in vec4 lightMapColor;
in vec4 overlayColor;
in vec2 texCoord0;
in vec4 normal;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0);

    float minV = 0.5;
    float maxV = 0.625;

    float distanceFromTip = (texCoord0.y - minV) / (maxV - minV);

    float maxGlowIntensity = 5.0;
    float falloffDistance = 0.65;

    float glowFactor = max(0.0, (falloffDistance - distanceFromTip) / falloffDistance);

    float greenLvl = 0.1215686275;
    float time = (greenLvl * sin(GameTime * 500)) + (greenLvl * 1.75);
//    vec4 glowColor = vec4(1.0, 0.243137255, 0.0, 1.0);
    vec4 glowColor = vec4(1.0, time, 0.0, 1.0);

    vec4 glowEffect = glowColor * glowFactor * maxGlowIntensity;

    color += glowEffect;

    color *= vertexColor * ColorModulator;
    color.rgb = mix(overlayColor.rgb, color.rgb, overlayColor.a);
    color *= lightMapColor;

    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}