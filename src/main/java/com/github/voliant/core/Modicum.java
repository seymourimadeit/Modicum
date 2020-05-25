package com.github.voliant.core;

import com.github.voliant.core.registry.MBlocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = Modicum.MODID)
public class Modicum {
    public static final String MODID = "glowingobsidian";
    public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MODID);

    public Modicum() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        REGISTRY_HELPER.getDeferredBlockRegister().register(modEventBus);
        REGISTRY_HELPER.getDeferredItemRegister().register(modEventBus);
    }
}
