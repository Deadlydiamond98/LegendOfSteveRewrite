package net.deadlydiamond.legend_of_steve.common.blocksets;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.switches.SwitchBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.switches.varient.SwitchSlabBlock;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.deadlydiamond.legend_of_steve.util.ZeldaModels;
import net.deadlydiamond98.koalalib.common.blocksets.AbstractBlockset;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
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

    private void addModel(String type) {
        MODEL_LOCATIONS.add(LegendOfSteve.id("block/" + id() + "_" + type));
    }

    // MODELS //////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void generateModels(BlockStateModelGenerator modelGen, @Nullable AbstractBlockset.SharedModel sharedModel) {
        super.generateModels(modelGen, sharedModel);
        registerBaseModel(modelGen, this.base);
    }

    public void registerBaseModel(BlockStateModelGenerator modelGen, Block block) {
        Identifier identifier = LegendOfSteve.id("block/" + id() + "_block");

        Identifier base = identifier.withSuffixedPath("_base");
        Identifier on = identifier.withSuffixedPath(this.isOn ? "_on" : "_off");

        ZeldaModels.SWITCH_BLOCK.upload(base, TextureMap.all(identifier).put(TextureKey.PARTICLE, on), modelGen.modelCollector);

        Models.CUBE_ALL.upload(on, TextureMap.all(on), modelGen.modelCollector);
        modelGen.registerParentedItemModel(block, on);

        modelGen.blockStateCollector.accept(VariantsBlockStateSupplier.create(block, BlockStateVariant.create().put(VariantSettings.MODEL, identifier)));
    }

    // SLAB ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void registerSlabModel(BlockStateModelGenerator modelGen, Block block) {
        Identifier identifier = LegendOfSteve.id("block/" + id() + "_slab");

        modelGen.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(
                block, identifier, identifier.withSuffixedPath("_top"),
                identifier.withSuffixedPath("_full")
        ));

        uploadSlabModels(modelGen, id());
        modelGen.registerParentedItemModel(block, identifier.withSuffixedPath("_on"));
    }

    public static void uploadSlabModels(BlockStateModelGenerator modelGen, String id) {
        uploadSlabModel(Models.SLAB, id, "_slab", modelGen, true);
        uploadSlabModel(Models.SLAB_TOP, id, "_slab_top", modelGen, true);
        uploadSlabModel(Models.CUBE_BOTTOM_TOP, id, "_slab_full", modelGen, true);

        uploadSlabModel(ZeldaModels.OFF_SLAB, id, "_slab", modelGen, false);
        uploadSlabModel(ZeldaModels.OFF_SLAB_TOP, id, "_slab_top", modelGen, false);
        uploadSlabModel(ZeldaModels.OFF_SLAB_FULL, id, "_slab_full", modelGen, false);
    }

    public static void uploadSlabModel(Model model, String id, String suffix, BlockStateModelGenerator modelGen, boolean isOn) {
        String type = isOn ? "_on" : "_off";
        Identifier modelID = LegendOfSteve.id("block/" + id);
        model.upload(modelID.withSuffixedPath(suffix + type), getSlabTextureMap(id, type), modelGen.modelCollector);
    }

    public static TextureMap getSlabTextureMap(String id, String type) {
        Identifier block = LegendOfSteve.id("block/" + id + "_block" + type);
        Identifier slab = LegendOfSteve.id("block/" + id + "_slab" + type);
        return TextureMap.of(TextureKey.TOP, block).put(TextureKey.BOTTOM, block).put(TextureKey.SIDE, slab)
                .put(TextureKey.PARTICLE, LegendOfSteve.id("block/" + id + "_block_on"));
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
}
