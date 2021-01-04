package com.github.abigailfails.modicum.core.registry;

import com.github.abigailfails.modicum.core.Modicum;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModicumContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Modicum.MOD_ID);

    //public static final RegistryObject<ContainerType<AssemblerContainer>> ASSEMBLER = CONTAINERS.register("assembler", () -> IForgeContainerType.create(AssemblerContainer::new));
}
