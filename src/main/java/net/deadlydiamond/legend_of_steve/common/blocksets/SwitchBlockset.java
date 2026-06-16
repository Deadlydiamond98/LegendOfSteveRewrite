package net.deadlydiamond.legend_of_steve.common.blocksets;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.bindable.BoundBlockUtil;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.bindable.switches.SwitchBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.bindable.switches.varient.SwitchSlabBlock;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.deadlydiamond.legend_of_steve.util.ZeldaModels;
import net.deadlydiamond98.koalalib.common.blocksets.AbstractBlockset;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

public class SwitchBlockset extends AbstractBlockset {
    public static final Set<Identifier> MODEL_LOCATIONS = new HashSet<>(); // These are used for custom block model
    private final boolean isOn;

    public final Block base;
    public final Block slab;

    public SwitchBlockset(String modID, String id, AbstractBlock.Settings settings, boolean isOn) {
        super(modID, id);
        this.isOn = isOn;

        this.base = register(modID, id() + "_block", new SwitchBlock(settings, isOn));
        this.slab = register(modID, id() + "_slab", new SwitchSlabBlock(settings, isOn));

        addModel("block");
        addModel("slab");
        addModel("slab_top");
        addModel("slab_full");
    }

    public void addSwitchesToCreative(ItemGroup.Entries entry) {
        this.blocks.forEach(block -> entry.add(BoundBlockUtil.getCreativeEntry(block.asItem().getDefaultStack())));
    }

    private void addModel(String type) {
        MODEL_LOCATIONS.add(LegendOfSteve.id("block/" + id() + "_" + type));
    }

    // MODELS //////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void generateModels(BlockStateModelGenerator modelGen, @Nullable AbstractBlockset.SharedModel sharedModel) {
        super.generateModels(modelGen, sharedModel);
        registerBaseModel(modelGen, this.base);
        registerSlabModel(modelGen, this.slab);
    }

    public void registerBaseModel(BlockStateModelGenerator modelGen, Block block) {
        Identifier identifier = LegendOfSteve.id("block/" + id() + "_block");
        Identifier texture = LegendOfSteve.id("block/switch/" + id() + "_block");

        Identifier base = identifier.withSuffixedPath("_base");

        ZeldaModels.SWITCH_BLOCK.upload(base, TextureMap.all(texture).put(TextureKey.PARTICLE, texture), modelGen.modelCollector);

        modelGen.registerParentedItemModel(block, base);

        modelGen.blockStateCollector.accept(VariantsBlockStateSupplier.create(block, BlockStateVariant.create().put(VariantSettings.MODEL, identifier)));
    }

    // SLAB MODEL //////////////////////////////////////////////////////////////////////////////////////////////////////

    public void registerSlabModel(BlockStateModelGenerator modelGen, Block block) {
        Identifier identifier = LegendOfSteve.id("block/" + id() + "_slab");

        modelGen.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(
                block, identifier, identifier.withSuffixedPath("_top"),
                identifier.withSuffixedPath("_full")
        ));

        ZeldaModels.SWITCH_SLAB.upload(identifier.withSuffixedPath("_base"), getSlabTextureMap(), modelGen.modelCollector);
        ZeldaModels.SWITCH_SLAB_FULL.upload(identifier.withSuffixedPath("_full_base"), getSlabTextureMap(), modelGen.modelCollector);
        ZeldaModels.SWITCH_SLAB_TOP.upload(identifier.withSuffixedPath("_top_base"), getSlabTextureMap(), modelGen.modelCollector);

        modelGen.registerParentedItemModel(block, identifier.withSuffixedPath("_base"));
    }

    public TextureMap getSlabTextureMap() {
        Identifier block = LegendOfSteve.id("block/switch/" + id() + "_block");
        Identifier slab = LegendOfSteve.id("block/switch/" + id() + "_slab");
        return TextureMap.of(TextureKey.TOP, block).put(TextureKey.BOTTOM, block).put(TextureKey.SIDE, slab)
                .put(TextureKey.PARTICLE, block);
    }

    // TAGS ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void generateItemTags(BiConsumer<TagKey<Item>, ItemConvertible> tagConsumer) {
        super.generateItemTags(tagConsumer);
        tagConsumer.accept(ItemTags.SLABS, this.slab);
    }

    @Override
    public void generateBlockTags(BiConsumer<TagKey<Block>, Block> tagConsumer, TagKey<Block>... mineableTags) {
        super.generateBlockTags(tagConsumer, mineableTags);

        TagKey<Block> switchTag = this.isOn ? ZeldaTags.RED_SWITCH_BLOCKS : ZeldaTags.BLUE_SWITCH_BLOCKS;
        for (Block block : getAll()) {
            tagConsumer.accept(switchTag, block);
        }

        tagConsumer.accept(BlockTags.SLABS, this.slab);
    }

    // LOOT TABLES /////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void generateLootTable(FabricBlockLootTableProvider lootTableProvider, Block block) {
        lootTableProvider.addDrop(block, BoundBlockUtil.lootTable(lootTableProvider, block));
    }
}
