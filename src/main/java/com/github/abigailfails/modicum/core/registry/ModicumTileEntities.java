package com.github.abigailfails.modicum.core.registry;

import com.github.abigailfails.modicum.common.tileentity.AssemblerTileEntity;
import com.github.abigailfails.modicum.core.Modicum;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModicumTileEntities {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Modicum.MOD_ID);
    //data fixer type cannot be null! Looks like assembler's going to crash for the time being
    //public static final RegistryObject<TileEntityType<AssemblerTileEntity>> ASSEMBLER = TILE_ENTITIES.register("assembler", () -> TileEntityType.Builder.create(AssemblerTileEntity::new, ModicumBlocks.ASSEMBLER.get()).build(null));
}
