package net.deadlydiamond.legend_of_steve.common.blocksets;

import net.deadlydiamond.legend_of_steve.common.blocks.functional.locks.lock.LockedBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.locks.lock.LockedChestBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.locks.lock.LockedDoorBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.locks.lock.LockedTrapdoorBlock;
import net.deadlydiamond.legend_of_steve.common.items.locking.KeyItem;
import net.deadlydiamond.legend_of_steve.common.items.locking.LockItem;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.deadlydiamond.legend_of_steve.util.datagen.model.ZeldaBlockModelDatagenUtil;
import net.deadlydiamond98.koalalib.common.blocksets.AbstractBlockset;
import net.deadlydiamond98.koalalib.common.items.interaction.PickupSoundItem;
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

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class LockBlockset extends AbstractBlockset {
    private final TagKey<Item> keyTag;

    public final Item lockItem;
    public final Item keyItem;

    public final Block lockedBlock;
    public final Block lockedChest;
    public final Block lockedDoor;
    public final Block lockedTrapdoor;

    public LockBlockset(String modID, String material, TagKey<Item> keyTag, Item.Settings settings) {
        super(modID, material);
        this.keyTag = keyTag;

        AbstractBlock.Settings blockSettings = FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
                .nonOpaque()
                .instrument(Instrument.SNARE).strength(55, 1200);

        this.lockedBlock = registerNoItem(modID, id() + "_lock", new LockedBlock(blockSettings, this.keyTag));
        this.lockedChest = registerNoItem(modID, id() + "_locked_chest", new LockedChestBlock(blockSettings, this.keyTag));
        this.lockedDoor = registerNoItem(modID, id() + "_locked_door", new LockedDoorBlock(blockSettings, this.keyTag));
        this.lockedTrapdoor = registerNoItem(modID, id() + "_locked_trapdoor", new LockedTrapdoorBlock(blockSettings, this.keyTag));

        this.lockItem = registerItem(Identifier.of(modID, id() + "_lock"), new LockItem(new FabricItemSettings(), this));
        this.keyItem = registerItem(Identifier.of(modID, id() + "_key"), new KeyItem(settings));
    }

    @Override
    public void generateModels(BlockStateModelGenerator modelGen, @Nullable AbstractBlockset.SharedModel sharedModel) {
        ZeldaBlockModelDatagenUtil.registerLockBlock(modelGen, this.lockedBlock);
        ZeldaBlockModelDatagenUtil.registerChestLockBlock(modelGen, this.lockedChest, this.lockedBlock);
        ZeldaBlockModelDatagenUtil.registerDoorLockBlock(modelGen, this.lockedDoor, this.lockedBlock);
        ZeldaBlockModelDatagenUtil.registerTrapdoorLockBlock(modelGen, this.lockedTrapdoor, this.lockedBlock);
    }

    @Override
    public void generateItemTags(BiConsumer<TagKey<Item>, ItemConvertible> tagConsumer) {
        tagConsumer.accept(this.keyTag, this.keyItem);
        tagConsumer.accept(ZeldaTags.LOCKS, this.lockItem);
    }

    // BLOCK GETTERS ///////////////////////////////////////////////////////////////////////////////////////////////////

    public static Block[] getAllLockBlocks() {
        return getBlocks((blocks, blockset) -> {
            blocks.add(blockset.lockedBlock);
            blocks.add(blockset.lockedChest);
            blocks.add(blockset.lockedDoor);
            blocks.add(blockset.lockedTrapdoor);
        });
    }

    public static Block[] getNonChestLocks() {
        return getBlocks((blocks, blockset) -> {
            blocks.add(blockset.lockedBlock);
            blocks.add(blockset.lockedDoor);
            blocks.add(blockset.lockedTrapdoor);
        });
    }

    public static Block[] getChestLocks() {
        return getBlocks((blocks, blockset) -> blocks.add(blockset.lockedChest));
    }

    private static Block[] getBlocks(BiConsumer<List<Block>, LockBlockset> consumer) {
        List<Block> blocks = new ArrayList<>();

        for (LockBlockset lock : ZeldaBlocks.LOCKS) {
            consumer.accept(blocks, lock);
        }

        return blocks.toArray(Block[]::new);
    }
}
