package com.github.abigailfails.core.registry;

import com.github.abigailfails.common.GlowingObsidianBlock;
import com.github.abigailfails.core.Modicum;
import com.teamabnormals.abnormals_core.core.utils.RegistryHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Modicum.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MBlocks {
    public static final RegistryHelper HELPER = Modicum.REGISTRY_HELPER;

    public static RegistryObject<Block> GLOWING_OBSIDIAN = HELPER.createBlock("glowing_obsidian", GlowingObsidianBlock::new, ItemGroup.BUILDING_BLOCKS);
}
