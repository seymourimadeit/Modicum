package com.github.abigailfails.modicum.common.tileentity;

import com.github.abigailfails.modicum.common.block.container.AssemblerContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.DispenserTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.ParametersAreNonnullByDefault;

public class AssemblerTileEntity extends DispenserTileEntity {

    public AssemblerTileEntity() {
        //super(ModicumTileEntities.ASSEMBLER.get());
    }

    @Override
    public int getDispenseSlot() {
        return -1;
    }

    @Override
    public int addItemStack(ItemStack stack) {
        return -1;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.modicum.assembler");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new AssemblerContainer(id, player, this);
    }
}
