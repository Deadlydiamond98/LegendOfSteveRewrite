package net.deadlydiamond.legend_of_steve.mixin.client.shader;

import net.deadlydiamond.legend_of_steve.util.mixinterfaces.client.ICustomUniforms;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.resource.ResourceFactory;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShaderProgram.class)
public abstract class ShaderProgramMixin implements ICustomUniforms {

    @Shadow public abstract @Nullable GlUniform getUniform(String name);

    @Unique @Nullable public GlUniform legend_of_steve$iridescenceItemOffset;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void legend_of_steve$init(ResourceFactory factory, String name, VertexFormat format, CallbackInfo ci) {
        this.legend_of_steve$iridescenceItemOffset = this.getUniform("LegendOfSteveIridescenceItemOffset");
    }

    @Override
    public void legend_of_steve$updateIridescenceItemOffset(float[] floats) {
        if (this.legend_of_steve$iridescenceItemOffset != null) {
            this.legend_of_steve$iridescenceItemOffset.set(floats);
        }
    }
}
