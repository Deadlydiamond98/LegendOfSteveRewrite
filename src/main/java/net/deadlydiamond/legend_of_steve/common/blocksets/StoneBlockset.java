package net.deadlydiamond.legend_of_steve.common.blocksets;

import net.deadlydiamond.legend_of_steve.util.datagen.ZeldaBlockModelDatagenUtil;
import net.deadlydiamond98.koalalib.common.blocksets.BaseStairSlabWallBlockset;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.util.Identifier;

public class StoneBlockset extends BaseStairSlabWallBlockset {

    public final BlockSetType blockSetType;

    public final Block button;
    public final Block plate;

    public StoneBlockset(String modID, String id, AbstractBlock.Settings settings) {
        this(modID, id, settings, BlockSetType.STONE);
    }

    public StoneBlockset(String modID, String id, AbstractBlock.Settings settings, BlockSetType blockSetTypeReference) {
        this(modID, id, settings, blockSetTypeReference, true);
    }

    public StoneBlockset(String modID, String id, AbstractBlock.Settings settings, BlockSetType blockSetTypeReference, boolean stripEndS) {
        super(modID, id, settings, stripEndS);

        this.blockSetType = BlockSetTypeBuilder.copyOf(blockSetTypeReference).build(new Identifier(modID, id));

        this.button = this.register(modID, this.id() + "_button", new ButtonBlock(
                settings.noCollision().strength(0.5f).pistonBehavior(PistonBehavior.DESTROY),
                this.blockSetType, 20, false
        ));

        this.plate = this.register(modID, this.id() + "_pressure_plate", new PressurePlateBlock(
                PressurePlateBlock.ActivationRule.MOBS,
                settings.noCollision().strength(0.5f).pistonBehavior(PistonBehavior.DESTROY),
                this.blockSetType
        ));
    }

    @Override
    public void generateModels(BlockStateModelGenerator modelGen, boolean uniqueSlab) {
        super.generateModels(modelGen, uniqueSlab);
        ZeldaBlockModelDatagenUtil.registerButton(modelGen, this.button, this.base);
        ZeldaBlockModelDatagenUtil.registerPressurePlate(modelGen, this.plate, this.base);
    }
}
