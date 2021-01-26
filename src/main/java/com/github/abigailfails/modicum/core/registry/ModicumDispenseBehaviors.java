package com.github.abigailfails.modicum.core.registry;

import com.github.abigailfails.modicum.core.ModicumConfig;
import com.github.abigailfails.modicum.core.event.ModicumEvents;
import com.minecraftabnormals.abnormals_core.core.util.DataUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.tileentity.DispenserTileEntity;
import net.minecraft.tileentity.JukeboxTileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.Random;

import static com.minecraftabnormals.abnormals_core.core.util.BlockUtil.getEntitiesAtOffsetPos;
import static com.minecraftabnormals.abnormals_core.core.util.BlockUtil.getStateAtOffsetPos;
import static com.minecraftabnormals.abnormals_core.core.util.BlockUtil.offsetPos;

public class ModicumDispenseBehaviors {
    public static void registerBehaviors() {
        if (ModicumConfig.COMMON.cauldronDispenserBehavior.get()) {
            DataUtil.registerAlternativeDispenseBehavior(Items.POTION, (source, stack) -> getStateAtOffsetPos(source).getBlock() instanceof CauldronBlock && PotionUtils.getPotionFromItem(stack) == Potions.WATER, new OptionalDispenseBehavior() {
                @Override
                protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                    this.setSuccessful(false);
                    BlockState state = getStateAtOffsetPos(source);
                    if (state.get(CauldronBlock.LEVEL) != 3) {
                        ((CauldronBlock) state.getBlock()).setWaterLevel(source.getWorld(), offsetPos(source), state, state.get(CauldronBlock.LEVEL) + 1);
                        this.setSuccessful(true);
                        stack = new ItemStack(Items.GLASS_BOTTLE);
                    }
                    return stack;
                }

                @Override
                protected void playDispenseSound(IBlockSource source) {
                    if (this.isSuccessful()) {
                        source.getWorld().playSound(null, source.getBlockPos(), SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        source.getWorld().playSound(null, source.getBlockPos(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    } else {
                        source.getWorld().playSound(null, source.getBlockPos(), SoundEvents.BLOCK_DISPENSER_FAIL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    }
                }
            });
            DataUtil.registerAlternativeDispenseBehavior(Items.GLASS_BOTTLE, (source, stack) -> getStateAtOffsetPos(source).getBlock() instanceof CauldronBlock, new OptionalDispenseBehavior() {
                @Override
                protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                    this.setSuccessful(false);
                    BlockState state = getStateAtOffsetPos(source);
                    if (state.get(CauldronBlock.LEVEL) != 0) {
                        ((CauldronBlock) state.getBlock()).setWaterLevel(source.getWorld(), offsetPos(source), state, state.get(CauldronBlock.LEVEL) - 1);
                        this.setSuccessful(true);
                        ItemStack filled = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER);
                        stack.shrink(1);
                        if (stack.isEmpty()) {
                            stack = filled.copy();
                        } else if (source.<DispenserTileEntity>getBlockTileEntity().addItemStack(filled.copy()) < 0) {
                            new DefaultDispenseItemBehavior().dispense(source, filled.copy());
                        }
                    }
                    return stack;
                }

                @Override
                protected void playDispenseSound(IBlockSource source) {
                    if (this.isSuccessful()) {
                        source.getWorld().playSound(null, source.getBlockPos(), SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        source.getWorld().playSound(null, source.getBlockPos(), SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    } else {
                        source.getWorld().playSound(null, source.getBlockPos(), SoundEvents.BLOCK_DISPENSER_FAIL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    }
                }
            });
            DataUtil.registerAlternativeDispenseBehavior(Items.WATER_BUCKET, (source, stack) -> getStateAtOffsetPos(source).getBlock() instanceof CauldronBlock, new OptionalDispenseBehavior() {
                @Override
                protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                    this.setSuccessful(false);
                    BlockPos pos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
                    BlockState state = source.getWorld().getBlockState(pos);
                    if (state.get(CauldronBlock.LEVEL) == 0) {
                        ((CauldronBlock) state.getBlock()).setWaterLevel(source.getWorld(), pos, state, 3);
                        this.setSuccessful(true);
                        stack = new ItemStack(Items.BUCKET);
                    }
                    return stack;
                }

                @Override
                protected void playDispenseSound(IBlockSource source) {
                    if (this.isSuccessful()) {
                        source.getWorld().playSound(null, source.getBlockPos(), SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        source.getWorld().playSound(null, source.getBlockPos(), SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    } else {
                        source.getWorld().playSound(null, source.getBlockPos(), SoundEvents.BLOCK_DISPENSER_FAIL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    }
                }
            });
            DataUtil.registerAlternativeDispenseBehavior(Items.BUCKET, (source, stack) -> getStateAtOffsetPos(source).getBlock() instanceof CauldronBlock, new OptionalDispenseBehavior() {
                @Override
                protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                    this.setSuccessful(false);
                    BlockState state = getStateAtOffsetPos(source);
                    if (state.get(CauldronBlock.LEVEL) == 3) {
                        ((CauldronBlock) state.getBlock()).setWaterLevel(source.getWorld(), offsetPos(source), state, 0);
                        this.setSuccessful(true);
                        stack = new ItemStack(Items.WATER_BUCKET);
                    }
                    return stack;
                }

                @Override
                protected void playDispenseSound(IBlockSource source) {
                    if (this.isSuccessful()) {
                        source.getWorld().playSound(null, source.getBlockPos(), SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        source.getWorld().playSound(null, source.getBlockPos(), SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    } else {
                        source.getWorld().playSound(null, source.getBlockPos(), SoundEvents.BLOCK_DISPENSER_FAIL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    }
                }
            });
        }
        if (ModicumConfig.COMMON.jukeboxDispenserBehavior.get()) {
            ForgeRegistries.ITEMS.getEntries().stream().map(Map.Entry::getValue).filter(i -> i instanceof MusicDiscItem).forEach(i -> DataUtil.registerAlternativeDispenseBehavior(i, (source, stack) -> getStateAtOffsetPos(source).getBlock() instanceof JukeboxBlock, new DefaultDispenseItemBehavior() {
                @Override
                protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                    BlockPos pos = offsetPos(source);
                    JukeboxBlock jukebox = (JukeboxBlock) getStateAtOffsetPos(source).getBlock();
                    JukeboxTileEntity jukeboxTE = (JukeboxTileEntity) source.getWorld().getTileEntity(pos);
                    if (jukeboxTE != null) {
                        ItemStack newStack = jukeboxTE.getRecord();
                        jukebox.insertRecord(source.getWorld(), pos, getStateAtOffsetPos(source), stack);
                        source.getWorld().playEvent(null, 1010, pos, Item.getIdFromItem(stack.getItem()));
                        return newStack;
                    }
                    return stack;
                }
            }));
        }
        if (ModicumConfig.COMMON.tntDefusing.get()) {
            DataUtil.registerAlternativeDispenseBehavior(Items.SHEARS, (source, stack) -> !getEntitiesAtOffsetPos(source, TNTEntity.class).isEmpty(), new DefaultDispenseItemBehavior() {
                @Override
                protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                    TNTEntity tnt = getEntitiesAtOffsetPos(source, TNTEntity.class).get(0);
                    World world = source.getWorld();
                    world.playSound(null, tnt.getPosition(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, 1.0f, 1.0f);
                    if (stack.attemptDamageItem(1, source.getWorld().getRandom(), null)) stack.setCount(0); //might need to set count to 0
                    ModicumEvents.extinguishTNT(tnt, world);
                    return stack;
                }
            });
        }
    }
}
