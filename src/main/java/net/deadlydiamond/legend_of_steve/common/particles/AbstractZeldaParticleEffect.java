package net.deadlydiamond.legend_of_steve.common.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import org.joml.Vector3f;

public abstract class AbstractZeldaParticleEffect implements ParticleEffect {



    // READ & WRITE METHODS ////////////////////////////////////////////////////////////////////////////////////////////

//    public static Vector3f readColor(PacketByteBuf buf) {
//        return new Vector3f(buf.readFloat(), buf.readFloat(), buf.readFloat());
//    }
//
//    public static Vector3f readColor(StringReader reader) throws CommandSyntaxException {
//        reader.expect(' ');
//        float f = reader.readFloat();
//        reader.expect(' ');
//        float g = reader.readFloat();
//        reader.expect(' ');
//        float h = reader.readFloat();
//        return new Vector3f(f, g, h);
//    }
}
