// CREDITS TO Phanastrae
// THIS SHADER IS FROM OPERATION STARCLEAVE, WHICH IS LICENSED UNDER MIT
// https://github.com/Phanastrae/operation_starcleave

vec4 getShineColor(float dot, float shineStrength, sampler2D gradientTexture) {
    float f = 0.5 * (1. + dot); // dot = -1 => f = 0, dot = +1 => f = 1

    int id = int(1);
    float iridescenceTx = f * textureSize(gradientTexture, 0).x;

    vec4 texColorFloor = texelFetch(gradientTexture, ivec2(int(floor(iridescenceTx)), id), 0);
    vec4 texColorCeil = texelFetch(gradientTexture, ivec2(int(ceil(iridescenceTx)), id), 0);
    vec4 texColor = mix(texColorFloor, texColorCeil, fract(iridescenceTx));

    return vec4(texColor.rgb, texColor.a * shineStrength);
}

vec4 shineCol(sampler2D normalsTexture, sampler2D gradientTexture, vec3 iridescenceOffset) {
    vec2 s = vec2(textureSize(Sampler0, 0));

    vec2 scaledUV = texCoord0 * s;
    vec2 uv = scaledUV;

    vec2 dUVdx = dFdx(uv);
    vec2 dUVdy = dFdy(uv);
    mat2x3 dPdxy = mat2x3(dFdx(vertexPos), dFdy(vertexPos));

    vec4 normalMap = texture(normalsTexture, texCoord0);

    // calc dPdUV
    mat2 dUVdxy = mat2(dUVdx, dUVdy);
    mat2 dxydUV = inverse(dUVdxy);
    mat2x3 dPdUV = dPdxy * dxydUV;

    // calc actual normal from base normal, normal texture, and basis vectors
    vec3 tangent = normalize(dPdUV[0]);
    vec3 bitangent = normalize(-dPdUV[1]); // need to flip y
    // normal can sometimes be incorrect (e.g. on block models with rotated planes, the normal will be axis aligned)
    // correct this here by calculating normal from tangent and bitangent, and just using the input normal to correct the sign
    vec3 correctedNormal = cross(tangent, bitangent);
    correctedNormal = (dot(correctedNormal, vertexNormal) >= 0.) ? correctedNormal : -correctedNormal;

    mat3 transformMatrix = mat3(tangent, bitangent, correctedNormal);
    vec3 localNormal = normalize(normalMap.rgb * 2. - 1.);
    vec3 finalNormal = transformMatrix * localNormal;

    // calc relative position of texel center
    vec2 texelCenter = floor(scaledUV) + 0.5;
    vec2 texelCenterOffset = texelCenter - scaledUV;

    vec3 dP = dPdUV * texelCenterOffset;
    vec3 texelCenterPos = vertexPos + dP;

    texelCenterPos += iridescenceOffset;

    vec3 viewDir = normalize(texelCenterPos);
    float dot = dot(viewDir, finalNormal);

    float shineStrength = normalMap.a;
    vec4 shineColor = getShineColor(dot, shineStrength, gradientTexture);

    return shineColor;
}