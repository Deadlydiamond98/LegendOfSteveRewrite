package net.deadlydiamond.legend_of_steve.client.models.block.transforms;

import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public abstract class AbstractTransform implements RenderContext.QuadTransform {
    @Override
    public boolean transform(MutableQuadView quad) {
        BakedQuad bakedQuad = quad.toBakedQuad(0, null, false);

        for (int v = 0; v < 4; v++) {
            float x = Float.intBitsToFloat(bakedQuad.getVertexData()[(v * 8)]);
            float y = Float.intBitsToFloat(bakedQuad.getVertexData()[1 + (v * 8)]);
            float z = Float.intBitsToFloat(bakedQuad.getVertexData()[2 + (v * 8)]);

            Vec3d vec3d = getQuadVecTranslations(new QuadVec(x, y, z)).getVec3d();

            quad.pos(v, (float) vec3d.x, (float) vec3d.y, (float) vec3d.z);
        }

        Direction originalDirection = quad.cullFace();
        if (originalDirection != null) {
            quad.cullFace(getCullDirection(originalDirection));
        }

        return shouldRender(quad);
    }

    public abstract QuadVec getQuadVecTranslations(QuadVec quadVec);
    public abstract Direction getCullDirection(Direction originalDirection);

    public boolean shouldRender(MutableQuadView quad) {
        return true;
    }
}
