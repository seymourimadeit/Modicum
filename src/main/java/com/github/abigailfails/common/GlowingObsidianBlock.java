package com.github.abigailfails.common;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class GlowingObsidianBlock extends Block {
    public GlowingObsidianBlock() {
        super(Block.Properties.create(Material.ROCK, MaterialColor.OBSIDIAN).harvestLevel(5).hardnessAndResistance(5.0F, 1200.0F).sound(SoundType.STONE));
    }

    //TODO change to random tick
    @SuppressWarnings("deprecation")
    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        BubbleColumnBlock.placeBubbleColumn(worldIn, pos.up(), true);
        if(shouldBeCooled(worldIn, pos)/*&&rand.nextInt(400)==100*/){
            worldIn.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
        }
    }

    //TODO add proper pickaxe settings thing
    //TODO make it work with bubble column (forge PR?)

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        if (!entityIn.isImmuneToFire() && entityIn instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entityIn)) {
            entityIn.attackEntityFrom(DamageSource.HOT_FLOOR, 1.0F);
        }
        super.onEntityWalk(worldIn, pos, entityIn);
    }


    protected boolean shouldBeCooled(IBlockReader blockReaderIn, BlockPos blockPosIn) {
        for(Direction direction : Direction.values()) {
            IFluidState ifluidstate = blockReaderIn.getFluidState(blockPosIn.offset(direction));
            if (ifluidstate.isTagged(FluidTags.LAVA)) {
                return false;
            }
        }
        return true;
    }
}