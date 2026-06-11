package net.deadlydiamond.legend_of_steve.common.bes.grouping;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class BoundGroupBlockEntity extends BlockEntity implements IGroupBlockEntity {
    public static final String DEFAULT_GROUP = "Global";
    protected String groupID = DEFAULT_GROUP;

    public BoundGroupBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putString("blockGroupID", this.groupID);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        setGroupID(nbt.getString("blockGroupID"));
    }

    @Override
    public String getGroupID() {
        return this.groupID;
    }

    @Override
    public void setGroupID(String newID) {
        this.groupID = newID;
    }
}
