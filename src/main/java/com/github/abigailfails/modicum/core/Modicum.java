package com.github.abigailfails.modicum.core;

import com.github.abigailfails.modicum.core.event.MDispenseBehaviors;
import com.github.abigailfails.modicum.core.registry.ModicumBlocks;
import com.github.abigailfails.modicum.core.registry.ModicumItems;
import com.github.abigailfails.modicum.core.registry.ModicumTileEntities;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = Modicum.MODID)
public class Modicum {
    public static final String MODID = "modicum";

    public Modicum() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModicumBlocks.BLOCKS.register(bus);
        ModicumItems.ITEMS.register(bus);
        ModicumTileEntities.TILE_ENTITIES.register(bus);
        bus.addListener(this::commonSetup);
    }


    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(MDispenseBehaviors::registerBehaviors);
    }
}
