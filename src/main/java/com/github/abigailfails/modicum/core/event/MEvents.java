package com.github.abigailfails.modicum.core.event;

import com.github.abigailfails.modicum.core.Modicum;
import net.minecraft.block.BlockState;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = Modicum.MODID)
public class MEvents {

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        ItemStack stack = event.getItemStack();
        EquipmentSlotType slot = MobEntity.getSlotForItemStack(stack);
        PlayerEntity player = event.getPlayer();
        ItemStack slotItem = player.getItemStackFromSlot(slot);
        /*TODO decide whether to allow non-armor items - in vanilla these are blocks,
            which could make it annoying to place them (especially if I also allow right
            clicking with an empty slot for consistency), but automatic compatibility with modded
            non-armor items would be nice. Maybe have config white and blacklists?
        * */
        //slot != EquipmentSlotType.MAINHAND && slot != EquipmentSlotType.OFFHAND
        if (stack.getItem() instanceof ArmorItem || stack.getItem() instanceof ElytraItem) {
            if (slotItem != ItemStack.EMPTY && slotItem.getItem() != stack.getItem()) {
                ItemStack old = player.getItemStackFromSlot(slot);
                player.setItemStackToSlot(slot, stack);
                //setItemStackToSlot not used here as it plays a duplicate sound
                if (event.getHand() == Hand.MAIN_HAND) {
                    player.inventory.mainInventory.set(player.inventory.currentItem, old);
                } else player.inventory.offHandInventory.set(0, old);
                event.setCancellationResult(ActionResultType.SUCCESS);
                event.setCanceled(true);
            }
        }
    }

    //TODO when AC release is out, replace with AnimateTickEvent
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (event.phase == TickEvent.Phase.END && !minecraft.isGamePaused() && minecraft.world != null) {
            Random random = new Random();
            BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
            if(minecraft.player != null) {
                int x = MathHelper.floor(minecraft.player.getPosX());
                int y = MathHelper.floor(minecraft.player.getPosY());
                int z = MathHelper.floor(minecraft.player.getPosZ());
                for (int j = 0; j < 667; ++j) {
                    startAnimatingTick(minecraft.world, x, y, z, 16, random, blockpos$mutable);
                    startAnimatingTick(minecraft.world, x, y, z, 32, random, blockpos$mutable);
                }
            }
        }
    }

    public static void startAnimatingTick(ClientWorld world, int x, int y, int z, int offset, Random random, BlockPos.Mutable pos) {
        int i = x + world.rand.nextInt(offset) - world.rand.nextInt(offset);
        int j = y + world.rand.nextInt(offset) - world.rand.nextInt(offset);
        int k = z + world.rand.nextInt(offset) - world.rand.nextInt(offset);
        pos.setPos(i, j, k);
        BlockState blockstate = world.getBlockState(pos);
        animateTick(blockstate, world, pos, random);
        /*if (!blockstate.hasOpaqueCollisionShape(world, pos)) {
            world.getBiome(pos).getAmbientParticle().ifPresent((p_239135_2_) -> {
                if (p_239135_2_.shouldParticleSpawn(world.rand)) {
                    world.addParticle(p_239135_2_.getParticleOptions(), (double)pos.getX() + world.rand.nextDouble(), (double)pos.getY() + world.rand.nextDouble(), (double)pos.getZ() + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
                }

            });
        }*/
    }

    public static void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if(stateIn.hasProperty(JukeboxBlock.HAS_RECORD) && stateIn.get(JukeboxBlock.HAS_RECORD) /*&& rand.nextInt(10) == 0*/) {
            worldIn.addParticle(ParticleTypes.NOTE, pos.getX()+0.5, pos.getY()+1, pos.getZ()+0.5, 0.0D, 0.0D, 0.0D);
        }
    }

}
