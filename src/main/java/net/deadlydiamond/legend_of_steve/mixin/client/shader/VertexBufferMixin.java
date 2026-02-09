package net.deadlydiamond.legend_of_steve.mixin.client.shader;

import net.deadlydiamond.legend_of_steve.util.mixinterfaces.client.ICustomUniforms;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.VertexBuffer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VertexBuffer.class)
public class VertexBufferMixin {
    @Inject(method = "drawInternal", at = @At("RETURN"))
    private void legend_of_steve$drawInternal(Matrix4f viewMatrix, Matrix4f projectionMatrix, ShaderProgram program, CallbackInfo ci) {
        float progress = (System.currentTimeMillis() % 5000) / 5000.0f;
        double angle = progress * (2 * Math.PI);
        float dx = (float) Math.cos(angle) * 1600;
        float dy = (float) Math.sin(angle) * 1600;
        ((ICustomUniforms) program).legend_of_steve$updateIridescenceItemOffset(new float[] {dx, dy, -5000});
    }
}
