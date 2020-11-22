package com.github.abigailfails.modicum.core.event;

import com.github.abigailfails.modicum.core.Modicum;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Modicum.MODID)
public class MEvents {

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        ItemStack stack = event.getItemStack();
        EquipmentSlotType slot = MobEntity.getSlotForItemStack(stack);
        PlayerEntity player = event.getPlayer();
        ItemStack slotItem = player.getItemStackFromSlot(slot);
        /*TODO decide whether to allow non-armor items - in vanilla these are blocks,
            which could make it annoying to place them (especially if I also allow right
            clicking with an empty slot for consistency), but automatic compatibility with modded
            non-armor items would be nice. Maybe have config white and blacklists?
        * */
        //slot != EquipmentSlotType.MAINHAND && slot != EquipmentSlotType.OFFHAND
        if (stack.getItem() instanceof ArmorItem || stack.getItem() instanceof ElytraItem) {
            if (slotItem != ItemStack.EMPTY && slotItem.getItem() != stack.getItem()) {
                ItemStack old = player.getItemStackFromSlot(slot);
                player.setItemStackToSlot(slot, stack);
                //setItemStackToSlot not used here as it plays a duplicate sound
                if (event.getHand() == Hand.MAIN_HAND) {
                    player.inventory.mainInventory.set(player.inventory.currentItem, old);
                } else player.inventory.offHandInventory.set(0, old);
                event.setCancellationResult(ActionResultType.SUCCESS);
                event.setCanceled(true);
            }
        }
    }

}
