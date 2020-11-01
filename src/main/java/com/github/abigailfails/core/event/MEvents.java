package com.github.abigailfails.core.event;

import com.github.abigailfails.core.registry.MBlocks;
import com.github.abigailfails.core.Modicum;
import net.minecraft.block.Blocks;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Modicum.MODID)
public class MEvents {
    @SubscribeEvent
    public static void onFluidPlaceBlock(BlockEvent.FluidPlaceBlockEvent event){
        if(event.getNewState().getBlock() == Blocks.OBSIDIAN){
            event.setNewState(MBlocks.GLOWING_OBSIDIAN.get().getDefaultState());
        }
    }
}
