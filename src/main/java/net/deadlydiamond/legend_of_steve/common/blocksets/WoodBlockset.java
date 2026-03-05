package net.deadlydiamond.legend_of_steve.common.blocksets;

import net.deadlydiamond.legend_of_steve.common.blocks.sign.CustomHangingSignBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.sign.CustomSignBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.sign.CustomWallHangingSignBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.sign.CustomWallSignBlock;
import net.deadlydiamond.legend_of_steve.util.datagen.model.ZeldaBlockModelDatagenUtil;
import net.deadlydiamond98.koalalib.common.blocksets.AbstractBlockset;
import net.deadlydiamond98.koalalib.util.datagen.BlockModelDatagenUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.item.HangingSignItem;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class WoodBlockset extends AbstractBlockset {

    public final BlockSetType blockSetType;
    public final WoodType woodType;

    public final Block log;
    public final Block wood;
    public final Block strippedLog;
    public final Block strippedWood;

    public final Block plank;
    public final Block slab;
    public final Block stair;
    public final Block fence;
    public final Block gate;

    public final Block door;
    public final Block trapdoor;
    public final Block button;
    public final Block plate;

    public final Item signItem;
    public final Block sign;
    public final Block wallSign;

    public final Item hangingSignItem;
    public final Block hangingSign;
    public final Block wallHangingSign;

    public WoodBlockset(String modID, String type, AbstractBlock.Settings settings, BlockSetType blockSetTypeReference) {
        this(modID, type, settings, blockSetTypeReference, true);
    }

    public WoodBlockset(String modID, String type, AbstractBlock.Settings settings, BlockSetType blockSetTypeReference, boolean flammable) {
        super(modID, type);

        this.blockSetType = BlockSetTypeBuilder.copyOf(blockSetTypeReference).build(new Identifier(modID, type));
        this.woodType = new WoodType(type, this.blockSetType);

        // BLOCKS //////////////////////////////////////////////////////////////////////////////////////////////////////

        this.log = this.register(modID, this.id() + "_log", new PillarBlock(settings));
        this.wood = this.register(modID, this.id() + "_wood", new PillarBlock(settings));
        this.strippedLog = this.register(modID, "stripped_" + this.id() + "_log", new PillarBlock(settings));
        this.strippedWood = this.register(modID, "stripped_" + this.id() + "_wood", new PillarBlock(settings));

        this.plank = this.register(modID, this.id() + "_planks", new Block(settings));
        this.stair = this.register(modID, this.id() + "_stairs", new StairsBlock(this.plank.getDefaultState(), settings));
        this.slab = this.register(modID, this.id() + "_slab", new SlabBlock(settings));

        this.fence = this.register(modID, this.id() + "_fence", new FenceBlock(settings));
        this.gate = this.register(modID, this.id() + "_fence_gate", new FenceGateBlock(settings, this.woodType));

        this.door = this.register(modID, this.id() + "_door", new DoorBlock(FabricBlockSettings.copyOf(this.plank).nonOpaque(), this.blockSetType));
        this.trapdoor = this.register(modID, this.id() + "_trapdoor", new TrapdoorBlock(FabricBlockSettings.copyOf(this.plank).nonOpaque(), this.blockSetType));

        this.plate = this.register(modID, this.id() + "_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING,
                FabricBlockSettings.copyOf(this.plank).noCollision().strength(0.5F).pistonBehavior(PistonBehavior.DESTROY), this.blockSetType));
        this.button = this.register(modID, this.id() + "_button", new ButtonBlock(
                FabricBlockSettings.copyOf(this.plank).noCollision().strength(0.5F).pistonBehavior(PistonBehavior.DESTROY), this.blockSetType, 30, true));

        // SIGNS ///////////////////////////////////////////////////////////////////////////////////////////////////////

        Identifier signTexture = new Identifier(modID, "entity/signs/" + this.id());
        this.sign = this.registerNoItem(modID, this.id() + "_sign", new CustomSignBlock(FabricBlockSettings.copyOf(this.plank).noCollision().strength(1), this.woodType, signTexture));
        this.wallSign = this.registerNoItem(modID, this.id() + "_wall_sign", new CustomWallSignBlock(FabricBlockSettings.copyOf(this.plank).noCollision().strength(1).dropsLike(this.sign), this.woodType, signTexture));
        this.signItem = this.registerItem(new Identifier(modID, this.id() + "_sign"), new SignItem(new FabricItemSettings(), this.sign, this.wallSign));

        Identifier hangingSignTexture = new Identifier(modID, "entity/signs/hanging/" + this.id());
        this.hangingSign = this.registerNoItem(modID, this.id() + "_hanging_sign", new CustomHangingSignBlock(FabricBlockSettings.copyOf(this.plank).noCollision().strength(1), this.woodType, hangingSignTexture));
        this.wallHangingSign = this.registerNoItem(modID, this.id() + "_wall_hanging_sign", new CustomWallHangingSignBlock(FabricBlockSettings.copyOf(this.plank).noCollision().strength(1).dropsLike(this.sign), this.woodType, hangingSignTexture));
        this.hangingSignItem = this.registerItem(new Identifier(modID, this.id() + "_hanging_sign"), new HangingSignItem(this.hangingSign, this.wallHangingSign, new FabricItemSettings()));

        additionalWoodInit(flammable);
    }

    private void additionalWoodInit(boolean flammable) {
        StrippableBlockRegistry.register(this.log, this.strippedLog);
        StrippableBlockRegistry.register(this.wood, this.strippedWood);

        if (flammable) {
            FlammableBlockRegistry.getDefaultInstance().add(this.log, 5, 5);
            FlammableBlockRegistry.getDefaultInstance().add(this.wood, 5, 5);
            FlammableBlockRegistry.getDefaultInstance().add(this.strippedLog, 5, 5);
            FlammableBlockRegistry.getDefaultInstance().add(this.strippedWood, 5, 5);
            FlammableBlockRegistry.getDefaultInstance().add(this.plank, 5, 20);
            FlammableBlockRegistry.getDefaultInstance().add(this.slab, 5, 20);
            FlammableBlockRegistry.getDefaultInstance().add(this.stair, 5, 20);
            FlammableBlockRegistry.getDefaultInstance().add(this.fence, 5, 20);
            FlammableBlockRegistry.getDefaultInstance().add(this.gate, 5, 20);
        }
    }

    @Override
    public void generateModels(BlockStateModelGenerator modelGen, @Nullable AbstractBlockset.@Nullable SharedModel sharedModel) {
        modelGen.registerLog(this.log).log(this.log).wood(this.wood);
        modelGen.registerLog(this.strippedLog).log(this.strippedLog).wood(this.strippedWood);

        modelGen.registerSimpleCubeAll(this.plank);
        BlockModelDatagenUtil.registerSlab(modelGen, this.slab, this.plank);
        BlockModelDatagenUtil.registerStairs(modelGen, this.stair, this.plank);

        ZeldaBlockModelDatagenUtil.registerButton(modelGen, this.button, this.plank);
        ZeldaBlockModelDatagenUtil.registerPressurePlate(modelGen, this.plate, this.plank);

        ZeldaBlockModelDatagenUtil.registerFence(modelGen, this.fence, this.plank);
        ZeldaBlockModelDatagenUtil.registerFenceGate(modelGen, this.gate, this.plank);

        ZeldaBlockModelDatagenUtil.registerSign(modelGen, this.sign, this.plank);
        ZeldaBlockModelDatagenUtil.registerSign(modelGen, this.hangingSign, this.plank);

        modelGen.registerDoor(this.door);
        modelGen.registerOrientableTrapdoor(this.trapdoor);
    }

    protected final Item registerItem(Identifier id, Item item) {
        return Registry.register(Registries.ITEM, id, item);
    }

    protected final Block registerNoItem(String modID, String id, Block block) {
        return registerNoItem(new Identifier(modID, id), block);
    }

    protected final Block registerNoItem(Identifier id, Block block) {
        return Registry.register(Registries.BLOCK, id, block);
    }
}
