package com.github.voliant.core;

import net.minecraft.block.Blocks;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GlowingObsidian.MODID)
public class GOEvents {
    @SubscribeEvent
    public static void onFluidPlaceBlock(BlockEvent.FluidPlaceBlockEvent event){
        if(event.getNewState().getBlock() == Blocks.OBSIDIAN){
            event.setNewState(GORegistry.GLOWING_OBSIDIAN.get().getDefaultState());
        }
    }
}
