package com.github.abigailfails.modicum.common.block;

import com.github.abigailfails.modicum.common.tileentity.AssemblerTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.DropperBlock;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.tileentity.DispenserTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class AssemblerBlock extends DropperBlock {

    public AssemblerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new AssemblerTileEntity();
    }

    /*@Override
    protected void dispense(ServerWorld world, BlockPos pos) {
        CraftingInventory craftWindow =
    }*/

    @SuppressWarnings("deprecation")
    public int getComparatorInputOverride(BlockState blockState, World world, BlockPos pos) {
        int filled = 0;
        DispenserTileEntity assembler = ((DispenserTileEntity)world.getTileEntity(pos));
        for (int i=0; i<9; i++) {
            if (assembler != null && !assembler.getStackInSlot(i).isEmpty()) filled++;
        }
        return (filled * 15) / 9;
    }

}
