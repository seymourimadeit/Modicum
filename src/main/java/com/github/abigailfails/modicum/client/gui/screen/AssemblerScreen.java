package com.github.abigailfails.modicum.client.gui.screen;

import net.minecraft.client.gui.screen.inventory.DispenserScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.DispenserContainer;
import net.minecraft.util.text.ITextComponent;

public class AssemblerScreen extends DispenserScreen {
    public AssemblerScreen(DispenserContainer container, PlayerInventory playerInventory, ITextComponent textComponent) {
        super(container, playerInventory, textComponent);
    }
    //TODO design crafting table like gui, work out how to grey out item
}
