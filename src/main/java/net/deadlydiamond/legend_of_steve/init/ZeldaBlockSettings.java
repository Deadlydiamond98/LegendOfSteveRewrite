package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;

public class ZeldaBlockSettings {
    // BLOCK SET TYPES /////////////////////////////////////////////////////////////////////////////////////////////////
    public static final BlockSetType MASTER_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.IRON)
            .openableByHand(true)
            .soundGroup(ZeldaSounds.MASTER_BARS)
            .build(LegendOfSteve.id("master"));


    // BLOCK SETTINGS //////////////////////////////////////////////////////////////////////////////////////////////////

    // PLANTS
    public static final FabricBlockSettings BOMB_FLOWER_SETTINGS = FabricBlockSettings.copyOf(Blocks.MOSS_BLOCK)
            .sounds(ZeldaSounds.BOMB_FLOWER)
            .nonOpaque()
            .noCollision()
            .breakInstantly();

    // FAIRY LIGHT
    public static final FabricBlockSettings RED_FAIRY_LIGHT_SETTINGS = getFairyLight(MapColor.BRIGHT_RED);
    public static final FabricBlockSettings ORANGE_FAIRY_LIGHT_SETTINGS = getFairyLight(MapColor.ORANGE);
    public static final FabricBlockSettings YELLOW_FAIRY_LIGHT_SETTINGS = getFairyLight(MapColor.YELLOW);
    public static final FabricBlockSettings GREEN_FAIRY_LIGHT_SETTINGS = getFairyLight(MapColor.EMERALD_GREEN);
    public static final FabricBlockSettings BLUE_FAIRY_LIGHT_SETTINGS = getFairyLight(MapColor.DIAMOND_BLUE);
    public static final FabricBlockSettings PURPLE_FAIRY_LIGHT_SETTINGS = getFairyLight(MapColor.MAGENTA);
    public static final FabricBlockSettings PINK_FAIRY_LIGHT_SETTINGS = getFairyLight(MapColor.PINK);

    // FAIRY MARBLE
    public static final FabricBlockSettings FAIRY_MARBLE_SETTINGS = FabricBlockSettings.copyOf(Blocks.CALCITE)
            .mapColor(MapColor.WHITE).instrument(Instrument.BELL);

    // DEKU WOOD
    public static final FabricBlockSettings DEKU_WOOD_SETTINGS = FabricBlockSettings.copyOf(Blocks.CHERRY_PLANKS)
            .mapColor(MapColor.BROWN);
    public static final FabricBlockSettings DEKU_LEAVES_SETTINGS = FabricBlockSettings.copyOf(Blocks.CHERRY_LEAVES)
            .mapColor(MapColor.DARK_GREEN);
    public static final FabricBlockSettings DEKU_SAPLING_SETTINGS = FabricBlockSettings.copyOf(Blocks.CHERRY_SAPLING)
            .mapColor(MapColor.DARK_GREEN);

    // MASTER
    public static final FabricBlockSettings MASTER_ORE_BLOCK_SETTINGS = getMaster(7, false)
            .sounds(BlockSoundGroup.STONE)
            .mapColor(MapColor.STONE_GRAY);
    public static final FabricBlockSettings DEEPSLATE_MASTER_ORE_BLOCK_SETTINGS = getMaster(8.5f, false)
            .sounds(BlockSoundGroup.DEEPSLATE)
            .mapColor(MapColor.DEEPSLATE_GRAY);

    public static final FabricBlockSettings MASTER_BLOCK_SETTINGS = getMaster(50, false)
            .sounds(ZeldaSounds.MASTER_BLOCK);
    public static final FabricBlockSettings MASTER_SCRAP_SETTINGS = getMaster(7, false)
            .sounds(ZeldaSounds.MASTER_BLOCK);

    public static final FabricBlockSettings MASTER_PLATE_SETTINGS = getMaster(7, false);

    public static final FabricBlockSettings MASTER_BAR_SETTINGS = getMaster(5, true)
            .sounds(ZeldaSounds.MASTER_BARS);
    public static final FabricBlockSettings MASTER_CHAIN_SETTINGS = getMaster(5, true)
            .sounds(ZeldaSounds.MASTER_CHAIN);

    public static final FabricBlockSettings MASTER_DOOR_SETTINGS = getMaster(7, true)
            .sounds(ZeldaSounds.MASTER_BARS).pistonBehavior(PistonBehavior.DESTROY);
    public static final FabricBlockSettings MASTER_TRAPDOOR_SETTINGS = getMaster(7, true)
            .sounds(ZeldaSounds.MASTER_BARS).allowsSpawning(Blocks::never);
    public static final FabricBlockSettings MASTER_GIRDER_SETTINGS = getMaster(5, true)
            .sounds(ZeldaSounds.MASTER_BLOCK)
            .allowsSpawning(Blocks::never)
            .suffocates(Blocks::never)
            .blockVision(Blocks::never);

    // TEKTILES
    public static final FabricBlockSettings BLUE_TEKTILES_SETTINGS = FabricBlockSettings.copyOf(Blocks.BONE_BLOCK)
            .sounds(ZeldaSounds.TEKTILES)
            .mapColor(MapColor.BLUE);
    public static final FabricBlockSettings RED_TEKTILES_SETTINGS = FabricBlockSettings.copyOf(Blocks.BONE_BLOCK)
            .sounds(ZeldaSounds.TEKTILES)
            .mapColor(MapColor.RED);


    // HELPER METHODS //////////////////////////////////////////////////////////////////////////////////////////////////

    public static FabricBlockSettings getFairyLight(MapColor color) {
        return FabricBlockSettings.copyOf(Blocks.SEA_LANTERN)
                .mapColor(color);
    }

    public static FabricBlockSettings getMaster(float strength, boolean nonOpaque) {
        FabricBlockSettings settings = FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
                .sounds(ZeldaSounds.MASTER_PLATE)
                .strength(strength, 600)
                .instrument(Instrument.BIT)
                .mapColor(DyeColor.RED);
        settings = nonOpaque ? settings.nonOpaque() : settings;

        return settings;
    }
}
