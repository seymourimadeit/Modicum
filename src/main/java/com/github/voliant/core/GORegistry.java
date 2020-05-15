package com.github.voliant.core;

import com.github.voliant.common.GlowingObsidianBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class GORegistry {
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, GlowingObsidian.MODID);
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, GlowingObsidian.MODID);

    public static RegistryObject<Block> GLOWING_OBSIDIAN = BLOCKS.register("glowing_obsidian", () -> new GlowingObsidianBlock());
    public static RegistryObject<Item> GLOWING_OBSIDIAN_ITEM = ITEMS.register("glowing_obsidian", () -> new BlockItem(GLOWING_OBSIDIAN.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
}
