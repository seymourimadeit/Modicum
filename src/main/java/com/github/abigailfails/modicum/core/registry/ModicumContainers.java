package com.github.abigailfails.modicum.core.registry;

import com.github.abigailfails.modicum.common.block.container.AssemblerContainer;
import com.github.abigailfails.modicum.core.Modicum;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModicumContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Modicum.MOD_ID);
    //TODO work out how to register this!!
    //public static final RegistryObject<ContainerType<AssemblerContainer>> ASSEMBLER = CONTAINERS.register("assembler", () -> IForgeContainerType.create(AssemblerContainer::new));
}
