package com.github.abigailfails.modicum.core.registry;

import com.github.abigailfails.modicum.core.Modicum;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ModicumBlocks {
    //TODO switch to AC now it's installed
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Modicum.MOD_ID);

    //public static final RegistryObject<Block> ASSEMBLER = registerBlock("assembler", () -> new AssemblerBlock(AbstractBlock.Properties.from(Blocks.DROPPER)), ItemGroup.REDSTONE);
}
