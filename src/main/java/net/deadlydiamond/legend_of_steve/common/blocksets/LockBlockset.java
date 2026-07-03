package net.deadlydiamond.legend_of_steve.common.blocksets;

import net.deadlydiamond.legend_of_steve.common.blocks.functional.LockBlock;
import net.deadlydiamond.legend_of_steve.common.items.locking.KeyItem;
import net.deadlydiamond.legend_of_steve.common.items.locking.LockItem;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.deadlydiamond98.koalalib.common.blocksets.AbstractBlockset;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class LockBlockset extends AbstractBlockset {
    public final Item lockItem;
    public final Item keyItem;

    public final Block lockBlock;

    public LockBlockset(String modID, String id) {
        super(modID, id);

        this.lockBlock = register(modID, id() + "_locked_block", new LockBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));

        this.lockItem = registerItem(Identifier.of(modID, id() +  "_lock"), new LockItem(new FabricItemSettings(), this.lockBlock));
        this.keyItem = registerItem(Identifier.of(modID, id() +  "_key"), new KeyItem(new FabricItemSettings(), this.lockBlock));
    }

    @Override
    public void generateModels(BlockStateModelGenerator modelGen, @Nullable AbstractBlockset.SharedModel sharedModel) {
        super.generateModels(modelGen, sharedModel);
    }

    @Override
    public void generateBlockTags(BiConsumer<TagKey<Block>, Block> tagConsumer, TagKey<Block>... mineableTags) {

    }

    @Override
    public void generateItemTags(BiConsumer<TagKey<Item>, ItemConvertible> tagConsumer) {

    }
}
