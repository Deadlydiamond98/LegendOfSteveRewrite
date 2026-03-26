package net.deadlydiamond.legend_of_steve.common.blocksets;

import net.deadlydiamond.legend_of_steve.common.blocks.container.LootPotBlock;
import net.deadlydiamond.legend_of_steve.common.items.block_item.LootPotItem;
import net.deadlydiamond98.koalalib.common.blocksets.AbstractBlockset;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class LootPotBlockset extends AbstractBlockset {
    public final Block white;
    public final Block light_gray;
    public final Block gray;
    public final Block black;
    public final Block brown;
    public final Block red;
    public final Block orange;
    public final Block yellow;
    public final Block lime;
    public final Block green;
    public final Block cyan;
    public final Block light_blue;
    public final Block blue;
    public final Block purple;
    public final Block magenta;
    public final Block pink;

    public LootPotBlockset(String modID, String id, Block block) {
        super(modID, id);
        this.white = this.registerPot(modID, "white_" + this.id(), new LootPotBlock(FabricBlockSettings.copyOf(block).mapColor(DyeColor.WHITE)));
        this.light_gray = this.registerPot(modID, "light_gray_" + this.id(), new LootPotBlock(FabricBlockSettings.copyOf(block).mapColor(DyeColor.LIGHT_GRAY)));
        this.gray = this.registerPot(modID, "gray_" + this.id(), new LootPotBlock(FabricBlockSettings.copyOf(block).mapColor(DyeColor.GRAY)));
        this.black = this.registerPot(modID, "black_" + this.id(), new LootPotBlock(FabricBlockSettings.copyOf(block).mapColor(DyeColor.BLACK)));
        this.brown = this.registerPot(modID, "brown_" + this.id(), new LootPotBlock(FabricBlockSettings.copyOf(block).mapColor(DyeColor.BROWN)));
        this.red = this.registerPot(modID, "red_" + this.id(), new LootPotBlock(FabricBlockSettings.copyOf(block).mapColor(DyeColor.RED)));
        this.orange = this.registerPot(modID, "orange_" + this.id(), new LootPotBlock(FabricBlockSettings.copyOf(block).mapColor(DyeColor.ORANGE)));
        this.yellow = this.registerPot(modID, "yellow_" + this.id(), new LootPotBlock(FabricBlockSettings.copyOf(block).mapColor(DyeColor.YELLOW)));
        this.lime = this.registerPot(modID, "lime_" + this.id(), new LootPotBlock(FabricBlockSettings.copyOf(block).mapColor(DyeColor.LIME)));
        this.green = this.registerPot(modID, "green_" + this.id(), new LootPotBlock(FabricBlockSettings.copyOf(block).mapColor(DyeColor.GREEN)));
        this.cyan = this.registerPot(modID, "cyan_" + this.id(), new LootPotBlock(FabricBlockSettings.copyOf(block).mapColor(DyeColor.CYAN)));
        this.light_blue = this.registerPot(modID, "light_blue_" + this.id(), new LootPotBlock(FabricBlockSettings.copyOf(block).mapColor(DyeColor.LIGHT_BLUE)));
        this.blue = this.registerPot(modID, "blue_" + this.id(), new LootPotBlock(FabricBlockSettings.copyOf(block).mapColor(DyeColor.BLUE)));
        this.purple = this.registerPot(modID, "purple_" + this.id(), new LootPotBlock(FabricBlockSettings.copyOf(block).mapColor(DyeColor.PURPLE)));
        this.magenta = this.registerPot(modID, "magenta_" + this.id(), new LootPotBlock(FabricBlockSettings.copyOf(block).mapColor(DyeColor.MAGENTA)));
        this.pink = this.registerPot(modID, "pink_" + this.id(), new LootPotBlock(FabricBlockSettings.copyOf(block).mapColor(DyeColor.PINK)));
    }

    public void generateModels(BlockStateModelGenerator modelGen, @Nullable AbstractBlockset.@Nullable SharedModel sharedModel) {
        this.blocks.forEach((block) -> {
            String color = this.getColor(block);
            sharedModel.registerSharedModel(modelGen, block, color);
        });
    }

    private String getColor(Block block) {
        String[] words = Registries.BLOCK.getId(block).getPath().split("_");
        return words[0] + (words[0].contains("light") ? "_" + words[1] : "");
    }

    private Block registerPot(String modID, String name, Block block) {
        Identifier id = new Identifier(modID, name);
        Block registeredBlock = Registry.register(Registries.BLOCK, id, block);
        Registry.register(Registries.ITEM, id, new LootPotItem(block, new FabricItemSettings()));
        this.blocks.add(registeredBlock);
        return registeredBlock;
    }
}
