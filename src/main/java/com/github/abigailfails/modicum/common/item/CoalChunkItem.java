package com.github.abigailfails.modicum.common.item;

import com.minecraftabnormals.abnormals_core.core.util.item.filling.TargetedItemGroupFiller;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;

public class CoalChunkItem extends Item {
    private static final TargetedItemGroupFiller FILLER = new TargetedItemGroupFiller(Items.CHARCOAL::getItem);

    public CoalChunkItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return 200;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        FILLER.fillItem(this, group, items);
    }
}
