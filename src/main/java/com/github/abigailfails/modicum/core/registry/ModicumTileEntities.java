package com.github.abigailfails.modicum.core.registry;

import com.github.abigailfails.modicum.core.Modicum;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModicumTileEntities {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Modicum.MOD_ID);

    //public static final RegistryObject<TileEntityType<AssemblerTileEntity>> ASSEMBLER = TILE_ENTITIES.register("assembler", () -> TileEntityType.Builder.create(AssemblerTileEntity::new, ModicumBlocks.ASSEMBLER.get()).build(null));
}
