package com.github.abigailfails.modicum.core;

import com.github.abigailfails.modicum.core.registry.ModicumBlocks;
import com.github.abigailfails.modicum.core.registry.ModicumItems;
import com.github.abigailfails.modicum.core.registry.ModicumTileEntities;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = Modicum.MODID)
public class Modicum {
    public static final String MODID = "modicum";

    public Modicum() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModicumBlocks.BLOCKS.register(modEventBus);
        ModicumItems.ITEMS.register(modEventBus);
        ModicumTileEntities.TILE_ENTITIES.register(modEventBus);

    }
}
