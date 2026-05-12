package net.deadlydiamond.legend_of_steve.common.items.sword;

import net.deadlydiamond.legend_of_steve.common.entities.projectile.SwordBeamEntity;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond98.koalalib.common.items.interaction.ISwingAction;
import net.deadlydiamond98.koalalib.util.magic.MagicBarHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BeamSwordItem extends SwordItem implements ISwingAction {
    public BeamSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public static boolean canUseMagicSword(PlayerEntity player) {
        return player.isCreative() || player.getHealth() >= player.getMaxHealth() || MagicBarHelper.removeMana(player, 3);
    }

    @Override
    public void attack(World world, PlayerEntity playerEntity) {
        if (world.isClient) {
            return;
        }

        if (canUseMagicSword(playerEntity) && !playerEntity.getItemCooldownManager().isCoolingDown(this)) {

            SwordBeamEntity beam = new SwordBeamEntity(world, playerEntity, playerEntity.getMainHandStack());
            Vec3d vec3d = playerEntity.getRotationVec(1);
            beam.setVelocity(vec3d.x, vec3d.y, vec3d.z, 0.75f, 0.1f);
            world.spawnEntity(beam);

            playerEntity.getItemCooldownManager().set(this, 20);
            playerEntity.playSound(ZeldaSounds.SWORD_SHOOT, SoundCategory.PLAYERS, 1, 1);
        }
    }
}
