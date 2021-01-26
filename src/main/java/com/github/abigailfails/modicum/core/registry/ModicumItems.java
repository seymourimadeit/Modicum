package com.github.abigailfails.modicum.core.registry;

import com.github.abigailfails.modicum.common.item.CoalChunkItem;
import com.github.abigailfails.modicum.core.Modicum;
import com.minecraftabnormals.abnormals_core.core.util.registry.ItemSubRegistryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Modicum.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModicumItems {
    public static final ItemSubRegistryHelper HELPER = Modicum.REGISTRY_HELPER.getItemSubHelper();

    //TODO move fireball to crossbows mod
    //public static final RegistryObject<Item> COAL_CHUNK     = HELPER.createItem("coal_chunk", () -> new CoalChunkItem(new Item.Properties().group(ItemGroup.MATERIALS)));
    //public static final RegistryObject<Item> CHARCOAL_CHUNK = HELPER.createItem("charcoal_chunk", () -> new CoalChunkItem(new Item.Properties().group(ItemGroup.MATERIALS)));
}
