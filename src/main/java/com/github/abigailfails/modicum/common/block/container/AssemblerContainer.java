package com.github.abigailfails.modicum.common.block.container;

import com.github.abigailfails.modicum.common.tileentity.AssemblerTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.DispenserContainer;
import net.minecraft.network.PacketBuffer;
import org.lwjgl.system.CallbackI;

import java.util.Objects;

public class AssemblerContainer extends DispenserContainer {

    //Add factory constructor?
    public AssemblerContainer(int p_i50087_1_, PlayerInventory p_i50087_2_) {
        super(p_i50087_1_, p_i50087_2_);
    }

    public AssemblerContainer(int p_i50088_1_, PlayerInventory p_i50088_2_, IInventory p_i50088_3_) {
        super(p_i50088_1_, p_i50088_2_, p_i50088_3_);
    }

    public AssemblerContainer(int i, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(i, playerInventory, (AssemblerTileEntity) Objects.requireNonNull(playerInventory.player.world.getTileEntity(packetBuffer.readBlockPos()), "getTileEntity cannot return null!"));
    }
}
