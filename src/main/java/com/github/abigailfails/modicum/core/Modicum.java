package com.github.abigailfails.modicum.core;

import com.github.abigailfails.modicum.core.registry.ModicumDispenseBehaviors;
import com.github.abigailfails.modicum.core.registry.ModicumBlocks;
import com.github.abigailfails.modicum.core.registry.ModicumItems;
import com.github.abigailfails.modicum.core.registry.ModicumTileEntities;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = Modicum.MOD_ID)
public class Modicum {
    public static final String MOD_ID = "modicum";

    public Modicum() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModicumBlocks.BLOCKS.register(bus);
        ModicumItems.ITEMS.register(bus);
        ModicumTileEntities.TILE_ENTITIES.register(bus);
        bus.addListener(this::commonSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModicumConfig.COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ModicumConfig.CLIENT_SPEC);
    }


    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(ModicumDispenseBehaviors::registerBehaviors);
    }
}
