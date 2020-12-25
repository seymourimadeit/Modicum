package com.github.abigailfails.modicum.core.event;

import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BannerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.tileentity.JukeboxTileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

public class MDispenseBehaviors {
    public static void registerBehaviors() {
        //TODO predicate being weird
        registerAlternativeDispenseBehavior(Items.POTION, (source, stack ) -> stateAtOffsetPos(source).getBlock() instanceof CauldronBlock && PotionUtils.getPotionFromItem(stack) == Potions.WATER, new OptionalDispenseBehavior() {
            @Override
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                this.setSuccessful(false);
                BlockState state = stateAtOffsetPos(source);
                if(state.get(CauldronBlock.LEVEL) != 3) {
                    ((CauldronBlock) state.getBlock()).setWaterLevel(source.getWorld(), offsetPos(source), state, state.get(CauldronBlock.LEVEL) + 1);
                    this.setSuccessful(true);
                    stack = new ItemStack(Items.GLASS_BOTTLE);
                }
                return stack;
            }

            @Override
            protected void playDispenseSound(IBlockSource source) {
                if(this.isSuccessful()) {
                    source.getWorld().playSound(null, source.getBlockPos(), SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    source.getWorld().playSound(null, source.getBlockPos(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                } else {
                    source.getWorld().playSound(null, source.getBlockPos(), SoundEvents.BLOCK_DISPENSER_FAIL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }
        });
        registerAlternativeDispenseBehavior(Items.GLASS_BOTTLE, (source, stack ) -> stateAtOffsetPos(source).getBlock() instanceof CauldronBlock, new OptionalDispenseBehavior() {
            @Override
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                this.setSuccessful(false);
                BlockState state = stateAtOffsetPos(source);
                if(state.get(CauldronBlock.LEVEL) != 0) {
                    ((CauldronBlock) state.getBlock()).setWaterLevel(source.getWorld(), offsetPos(source), state, state.get(CauldronBlock.LEVEL) - 1);
                    this.setSuccessful(true);
                    stack = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER);
                }
                return stack;
            }

            @Override
            protected void playDispenseSound(IBlockSource source) {
                if(this.isSuccessful()) {
                    source.getWorld().playSound(null, source.getBlockPos(), SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    source.getWorld().playSound(null, source.getBlockPos(), SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                } else {
                    source.getWorld().playSound(null, source.getBlockPos(), SoundEvents.BLOCK_DISPENSER_FAIL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }
        });
        registerAlternativeDispenseBehavior(Items.WATER_BUCKET, (source, stack ) -> stateAtOffsetPos(source).getBlock() instanceof CauldronBlock, new OptionalDispenseBehavior() {
            @Override
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                this.setSuccessful(false);
                BlockPos pos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
                BlockState state = source.getWorld().getBlockState(pos);
                if(state.get(CauldronBlock.LEVEL) == 0) {
                    ((CauldronBlock) state.getBlock()).setWaterLevel(source.getWorld(), pos, state, 3);
                    this.setSuccessful(true);
                    stack = new ItemStack(Items.BUCKET);
                }
                return stack;
            }

            @Override
            protected void playDispenseSound(IBlockSource source) {
                if(this.isSuccessful()) {
                    source.getWorld().playSound(null, source.getBlockPos(), SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    source.getWorld().playSound(null, source.getBlockPos(), SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                } else {
                    source.getWorld().playSound(null, source.getBlockPos(), SoundEvents.BLOCK_DISPENSER_FAIL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }
        });
        registerAlternativeDispenseBehavior(Items.BUCKET, (source, stack ) -> stateAtOffsetPos(source).getBlock() instanceof CauldronBlock, new OptionalDispenseBehavior() {
            @Override
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                this.setSuccessful(false);
                BlockState state = stateAtOffsetPos(source);
                if(state.get(CauldronBlock.LEVEL) == 3) {
                    ((CauldronBlock) state.getBlock()).setWaterLevel(source.getWorld(), offsetPos(source), state, 0);
                    this.setSuccessful(true);
                    stack = new ItemStack(Items.WATER_BUCKET);
                }
                return stack;
            }

            @Override
            protected void playDispenseSound(IBlockSource source) {
                if(this.isSuccessful()) {
                    source.getWorld().playSound(null, source.getBlockPos(), SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    source.getWorld().playSound(null, source.getBlockPos(), SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                } else {
                    source.getWorld().playSound(null, source.getBlockPos(), SoundEvents.BLOCK_DISPENSER_FAIL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }
        });
        ForgeRegistries.ITEMS.getEntries().stream().map(Map.Entry::getValue).filter(i -> i instanceof MusicDiscItem).forEach(i -> registerAlternativeDispenseBehavior(i, (source, stack) -> stateAtOffsetPos(source).getBlock() instanceof JukeboxBlock, new DefaultDispenseItemBehavior() {
            @Override
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                BlockPos pos = offsetPos(source);
                JukeboxBlock jukebox = (JukeboxBlock) stateAtOffsetPos(source).getBlock();
                JukeboxTileEntity jukeboxTE = (JukeboxTileEntity) source.getWorld().getTileEntity(pos);
                if(jukeboxTE != null) {
                    ItemStack newStack = jukeboxTE.getRecord();
                    jukebox.insertRecord(source.getWorld(), pos, stateAtOffsetPos(source), stack);
                    source.getWorld().playEvent( null, 1010, pos, Item.getIdFromItem(stack.getItem()));
                    return newStack;
                }
                return stack; //should not happen
            }
        }));
    }

    public static void registerAlternativeDispenseBehavior(Item item, BiPredicate<IBlockSource, ItemStack> condition, IDispenseItemBehavior newBehavior) {
        IDispenseItemBehavior oldBehavior = DispenserBlock.DISPENSE_BEHAVIOR_REGISTRY.get(item);
        DispenserBlock.registerDispenseBehavior(item, (source, stack) -> condition.test(source, stack) ? newBehavior.dispense(source, stack) : oldBehavior.dispense(source, stack));
    }

    public static BlockPos offsetPos(IBlockSource source) {
        return source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
    }

    public static BlockState stateAtOffsetPos(IBlockSource source) {
        return source.getWorld().getBlockState(offsetPos(source));
    }

    public static List<Entity> entitiesAtOffsetPos(IBlockSource source, Class<Entity> entityType) {
        return source.getWorld().getEntitiesWithinAABB(entityType, new AxisAlignedBB(offsetPos(source)));
    }
}
