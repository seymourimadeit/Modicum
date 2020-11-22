package com.github.abigailfails.modicum.core.registry;

import com.github.abigailfails.modicum.common.block.AssemblerBlock;
import com.github.abigailfails.modicum.core.Modicum;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ModicumBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Modicum.MODID);

    //public static final RegistryObject<Block> ASSEMBLER = registerBlock("assembler", () -> new AssemblerBlock(AbstractBlock.Properties.from(Blocks.DROPPER)), ItemGroup.REDSTONE);

    public static RegistryObject<Block> registerBlock(String name, Supplier<Block> supplier, @Nullable ItemGroup group) {
        RegistryObject<Block> block = BLOCKS.register(name, supplier);
        ModicumItems.ITEMS.register(name, () -> new BlockItem(block.get(), (new Item.Properties()).group(group)));
        return block;
    }

}
