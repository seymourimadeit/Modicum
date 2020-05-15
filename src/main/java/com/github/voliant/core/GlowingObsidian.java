package com.github.voliant.core;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("glowingobsidian")
public class GlowingObsidian {
    public static final String MODID = "glowingobsidian";

    public GlowingObsidian() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        GORegistry.BLOCKS.register(modEventBus);
        GORegistry.ITEMS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }
}
