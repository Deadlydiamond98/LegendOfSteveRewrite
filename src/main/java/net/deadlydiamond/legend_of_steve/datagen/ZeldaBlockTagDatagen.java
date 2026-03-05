package net.deadlydiamond.legend_of_steve.datagen;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ZeldaBlockTagDatagen extends FabricTagProvider.BlockTagProvider {

    public ZeldaBlockTagDatagen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        registerMineable();
        registerBuilding();
    }

    private void registerMineable() {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(
                // LAMP
                ZeldaBlocks.PINK_FAIRY_LAMP,
                ZeldaBlocks.RED_FAIRY_LAMP,
                ZeldaBlocks.ORANGE_FAIRY_LAMP,
                ZeldaBlocks.YELLOW_FAIRY_LAMP,
                ZeldaBlocks.GREEN_FAIRY_LAMP,
                ZeldaBlocks.BLUE_FAIRY_LAMP,
                ZeldaBlocks.PURPLE_FAIRY_LAMP,

                // FAIRY MARBLE
                ZeldaBlocks.FAIRY_MARBLE.base,
                ZeldaBlocks.FAIRY_MARBLE.slab,
                ZeldaBlocks.FAIRY_MARBLE.stair,
                ZeldaBlocks.FAIRY_MARBLE.wall,
                ZeldaBlocks.FAIRY_MARBLE.button,
                ZeldaBlocks.FAIRY_MARBLE.plate,

                ZeldaBlocks.COBBLED_FAIRY_MARBLE.base,
                ZeldaBlocks.COBBLED_FAIRY_MARBLE.slab,
                ZeldaBlocks.COBBLED_FAIRY_MARBLE.stair,
                ZeldaBlocks.COBBLED_FAIRY_MARBLE.wall,

                ZeldaBlocks.POLISHED_FAIRY_MARBLE.base,
                ZeldaBlocks.POLISHED_FAIRY_MARBLE.slab,
                ZeldaBlocks.POLISHED_FAIRY_MARBLE.stair,
                ZeldaBlocks.POLISHED_FAIRY_MARBLE.wall,

                ZeldaBlocks.FAIRY_MARBLE_BRICKS.base,
                ZeldaBlocks.FAIRY_MARBLE_BRICKS.slab,
                ZeldaBlocks.FAIRY_MARBLE_BRICKS.stair,
                ZeldaBlocks.FAIRY_MARBLE_BRICKS.wall,

                ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.base,
                ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.slab,
                ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.stair,
                ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.wall,

                ZeldaBlocks.FAIRY_MARBLE_TILES.base,
                ZeldaBlocks.FAIRY_MARBLE_TILES.slab,
                ZeldaBlocks.FAIRY_MARBLE_TILES.stair,
                ZeldaBlocks.FAIRY_MARBLE_TILES.wall,

                ZeldaBlocks.CRACKED_FAIRY_MARBLE_BRICKS,
                ZeldaBlocks.CHISELED_FAIRY_MARBLE,
                ZeldaBlocks.FAIRY_MARBLE_PILLAR,
                ZeldaBlocks.SMOOTH_FAIRY_MARBLE,

                // MASTER
                ZeldaBlocks.MASTER_ORE,
                ZeldaBlocks.DEEPSLATE_MASTER_ORE,
                ZeldaBlocks.MASTER_SCRAP_BLOCK,
                ZeldaBlocks.MASTER_BLOCK,

                ZeldaBlocks.MASTER_PLATE.base,
                ZeldaBlocks.MASTER_PLATE.slab,
                ZeldaBlocks.MASTER_PLATE.stair,

                ZeldaBlocks.MASTER_BRICK.base,
                ZeldaBlocks.MASTER_BRICK.slab,
                ZeldaBlocks.MASTER_BRICK.stair,

                ZeldaBlocks.MASTER_TILE.base,
                ZeldaBlocks.MASTER_TILE.slab,
                ZeldaBlocks.MASTER_TILE.stair,

                ZeldaBlocks.CUT_MASTER_PLATE,
                ZeldaBlocks.MASTER_PILLAR,
                ZeldaBlocks.MASTER_BARS,
                ZeldaBlocks.MASTER_CHAIN,
                ZeldaBlocks.MASTER_DOOR,
                ZeldaBlocks.MASTER_TRAPDOOR,
                ZeldaBlocks.MASTER_GIRDER,
                ZeldaBlocks.MASTER_BARREL,

                // TEKTILES
                ZeldaBlocks.RED_TEKTILES.base,
                ZeldaBlocks.RED_TEKTILES.slab,
                ZeldaBlocks.RED_TEKTILES.stair,

                ZeldaBlocks.SMALL_RED_TEKTILES.base,
                ZeldaBlocks.SMALL_RED_TEKTILES.slab,
                ZeldaBlocks.SMALL_RED_TEKTILES.stair,

                ZeldaBlocks.BLUE_TEKTILES.base,
                ZeldaBlocks.BLUE_TEKTILES.slab,
                ZeldaBlocks.BLUE_TEKTILES.stair,

                ZeldaBlocks.SMALL_BLUE_TEKTILES.base,
                ZeldaBlocks.SMALL_BLUE_TEKTILES.slab,
                ZeldaBlocks.SMALL_BLUE_TEKTILES.stair
        );
    }

    private void registerBuilding() {
        // TREE RELATED ////////////////////////////////////////////////////////////////////////////////////////////////

        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).add(
                ZeldaBlocks.DEKU_WOOD.log,
                ZeldaBlocks.DEKU_WOOD.wood,
                ZeldaBlocks.DEKU_WOOD.strippedLog,
                ZeldaBlocks.DEKU_WOOD.strippedWood
        );

        getOrCreateTagBuilder(BlockTags.LEAVES).add(
                ZeldaBlocks.DEKU_LEAVES,
                ZeldaBlocks.FRUITING_DEKU_LEAVES
        );

        getOrCreateTagBuilder(BlockTags.PLANKS).add(
                ZeldaBlocks.DEKU_WOOD.plank
        );

        // SLABS ///////////////////////////////////////////////////////////////////////////////////////////////////////

        getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(
                ZeldaBlocks.DEKU_WOOD.slab
        );

        getOrCreateTagBuilder(BlockTags.SLABS).add(
                // Fairy Marble
                ZeldaBlocks.FAIRY_MARBLE.slab,
                ZeldaBlocks.COBBLED_FAIRY_MARBLE.slab,
                ZeldaBlocks.POLISHED_FAIRY_MARBLE.slab,
                ZeldaBlocks.FAIRY_MARBLE_BRICKS.slab,
                ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.slab,
                ZeldaBlocks.FAIRY_MARBLE_TILES.slab,
                // Master
                ZeldaBlocks.MASTER_PLATE.slab,
                ZeldaBlocks.MASTER_BRICK.slab,
                ZeldaBlocks.MASTER_TILE.slab,
                // TEKTILES
                ZeldaBlocks.RED_TEKTILES.slab,
                ZeldaBlocks.SMALL_RED_TEKTILES.slab,
                ZeldaBlocks.BLUE_TEKTILES.slab,
                ZeldaBlocks.SMALL_BLUE_TEKTILES.slab
        );

        // STAIRS //////////////////////////////////////////////////////////////////////////////////////////////////////

        getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(
                ZeldaBlocks.DEKU_WOOD.stair
        );

        getOrCreateTagBuilder(BlockTags.STAIRS).add(
                // Fairy Marble
                ZeldaBlocks.FAIRY_MARBLE.stair,
                ZeldaBlocks.COBBLED_FAIRY_MARBLE.stair,
                ZeldaBlocks.POLISHED_FAIRY_MARBLE.stair,
                ZeldaBlocks.FAIRY_MARBLE_BRICKS.stair,
                ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.stair,
                ZeldaBlocks.FAIRY_MARBLE_TILES.stair,
                // Master
                ZeldaBlocks.MASTER_PLATE.stair,
                ZeldaBlocks.MASTER_BRICK.stair,
                ZeldaBlocks.MASTER_TILE.stair,
                // TEKTILES
                ZeldaBlocks.RED_TEKTILES.stair,
                ZeldaBlocks.SMALL_RED_TEKTILES.stair,
                ZeldaBlocks.BLUE_TEKTILES.stair,
                ZeldaBlocks.SMALL_BLUE_TEKTILES.stair
        );

        // FENCE ///////////////////////////////////////////////////////////////////////////////////////////////////////

        getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(
                ZeldaBlocks.DEKU_WOOD.fence
        );

        getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(
                ZeldaBlocks.DEKU_WOOD.gate
        );

        // WALL ////////////////////////////////////////////////////////////////////////////////////////////////////////

        getOrCreateTagBuilder(BlockTags.WALLS).add(
                ZeldaBlocks.FAIRY_MARBLE.wall,
                ZeldaBlocks.COBBLED_FAIRY_MARBLE.wall,
                ZeldaBlocks.POLISHED_FAIRY_MARBLE.wall,
                ZeldaBlocks.FAIRY_MARBLE_BRICKS.wall,
                ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.wall,
                ZeldaBlocks.FAIRY_MARBLE_TILES.wall
        );

        // BUTTONS & PLATES

        getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(
                ZeldaBlocks.DEKU_WOOD.button
        );

        getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(
                ZeldaBlocks.DEKU_WOOD.plate
        );

        // DOORS & TRAPDOORS

        getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(
                ZeldaBlocks.DEKU_WOOD.door
        );

        getOrCreateTagBuilder(BlockTags.TRAPDOORS).add(
                ZeldaBlocks.DEKU_WOOD.trapdoor
        );

        // SIGNS

        getOrCreateTagBuilder(BlockTags.STANDING_SIGNS).add(
                ZeldaBlocks.DEKU_WOOD.sign
        );

        getOrCreateTagBuilder(BlockTags.WALL_SIGNS).add(
                ZeldaBlocks.DEKU_WOOD.wallSign
        );

        getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS).add(
                ZeldaBlocks.DEKU_WOOD.hangingSign
        );

        getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS).add(
                ZeldaBlocks.DEKU_WOOD.wallHangingSign
        );
    }
}
