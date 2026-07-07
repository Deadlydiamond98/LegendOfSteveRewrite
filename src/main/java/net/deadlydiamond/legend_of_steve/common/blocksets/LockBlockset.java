package net.deadlydiamond.legend_of_steve.common.blocksets;

import net.deadlydiamond.legend_of_steve.common.blocks.functional.locks.LockedBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.locks.LockedChestBlock;
import net.deadlydiamond.legend_of_steve.common.items.locking.LockItem;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.deadlydiamond.legend_of_steve.util.datagen.model.ZeldaBlockModelDatagenUtil;
import net.deadlydiamond98.koalalib.common.blocksets.AbstractBlockset;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.Instrument;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class LockBlockset extends AbstractBlockset {
    private final TagKey<Item> keyTag;

    public final Item lockItem;
    public final Item keyItem;

    public final Block lockedBlock;
    public final Block lockedChest;

    public LockBlockset(String modID, String material, TagKey<Item> keyTag, Item.Settings settings) {
        super(modID, material);
        this.keyTag = keyTag;

        AbstractBlock.Settings blockSettings = FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).instrument(Instrument.SNARE);

        this.lockedBlock = registerNoItem(modID, id() + "_lock", new LockedBlock(blockSettings, this.keyTag));
        this.lockedChest = registerNoItem(modID, id() + "_locked_chest", new LockedChestBlock(blockSettings, this.keyTag));

        this.lockItem = registerItem(Identifier.of(modID, id() + "_lock"), new LockItem(new FabricItemSettings(), this));
        this.keyItem = registerItem(Identifier.of(modID, id() + "_key"), new Item(settings));
    }

    @Override
    public void generateModels(BlockStateModelGenerator modelGen, @Nullable AbstractBlockset.SharedModel sharedModel) {
        ZeldaBlockModelDatagenUtil.registerLockBlock(modelGen, this.lockedBlock);
        ZeldaBlockModelDatagenUtil.registerChestLockBlock(modelGen, this.lockedChest, this.lockedBlock);
    }

    @Override
    public void generateItemTags(BiConsumer<TagKey<Item>, ItemConvertible> tagConsumer) {
        tagConsumer.accept(this.keyTag, this.keyItem);
        tagConsumer.accept(ZeldaTags.LOCKS, this.lockItem);
    }
}
