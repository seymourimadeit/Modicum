package com.github.abigailfails.modicum.core.event;

import com.github.abigailfails.modicum.core.Modicum;
import com.github.abigailfails.modicum.core.ModicumConfig;
import com.minecraftabnormals.abnormals_core.core.events.AnimateTickEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.TNTBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = Modicum.MOD_ID)
public class ModicumEvents {

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        ItemStack stack = event.getItemStack();
        PlayerEntity player = event.getPlayer();
        World world = event.getWorld();

        if (ModicumConfig.COMMON.hotbarArmorSwapping.get()) {
            EquipmentSlotType slot = MobEntity.getSlotForItemStack(stack);
            ItemStack slotItem = player.getItemStackFromSlot(slot);
            /* Not sure whether to allow non-armor items - in vanilla these are blocks,
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
                    event.setCancellationResult(ActionResultType.func_233537_a_(world.isRemote));
                    event.setCanceled(true);
                }
            }
        }
        if (!event.isCanceled() && ModicumConfig.COMMON.tntDropping.get() && !player.getCooldownTracker().hasCooldown(Items.TNT) /*&& player.isElytraFlying()*/) {
            ItemStack offhandStack = player.getItemStackFromSlot(EquipmentSlotType.OFFHAND);
            Item mainItem = stack.getItem();
            Item offhandItem = offhandStack.getItem();
            ItemStack TNTStack = mainItem instanceof BlockItem && ((BlockItem) mainItem).getBlock() instanceof TNTBlock ? stack : offhandItem instanceof BlockItem && ((BlockItem) offhandItem).getBlock() instanceof TNTBlock ? offhandStack : null;
            ItemStack lighterStack = mainItem == Items.FLINT_AND_STEEL || mainItem == Items.FIRE_CHARGE ? stack : offhandItem == Items.FLINT_AND_STEEL || offhandItem == Items.FIRE_CHARGE ? offhandStack : null;
            if (TNTStack != null && lighterStack != null) {
                BlockPos pos = event.getPos().offset(player.getHorizontalFacing());
                if(!world.getBlockState(pos).isOpaqueCube(world, pos)) {
                    //Ugly way of getting any tnt-extending item to spawn its entity
                    BlockState tntState = ((BlockItem) TNTStack.getItem()).getBlock().getDefaultState();
                    tntState.catchFire(world, pos, null, player);
                    world.playSound(null, pos, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.PLAYERS, 1.0f, 1.0f);
                    if (!player.isCreative()) {
                        TNTStack.shrink(1);
                        if (lighterStack.getItem() == Items.FLINT_AND_STEEL) {
                            lighterStack.damageItem(1, player, (playerIn) -> playerIn.sendBreakAnimation(event.getHand()));
                        } else lighterStack.shrink(1);
                    }
                    player.getCooldownTracker().setCooldown(Items.TNT, 60);
                    event.setCancellationResult(ActionResultType.func_233537_a_(world.isRemote));
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        Entity target = event.getTarget();
        ItemStack stack = event.getItemStack();
        if (ModicumConfig.COMMON.tntDefusing.get() && stack.getItem() instanceof ShearsItem && target instanceof TNTEntity) {
            World world = event.getWorld();
            BlockPos pos = event.getPos();
            Random random = world.getRandom();
            target.remove();
            world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, 1.0f, 1.0f);
            stack.damageItem(1, event.getPlayer(), p -> p.sendBreakAnimation(event.getHand()));
            for (int i=0; i<5; i++) {
                double posX = pos.getX() + random.nextFloat();
                double posY = pos.getY() + random.nextFloat();
                double posZ = pos.getZ() + random.nextFloat();
                world.addParticle(ParticleTypes.LARGE_SMOKE, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
            }
            event.setCancellationResult(ActionResultType.func_233537_a_(world.isRemote()));
            event.setCanceled(true);
        }

    }

    @SubscribeEvent
    public static void onClientTick(AnimateTickEvent event) {
        if (ModicumConfig.CLIENT.jukeboxParticles.get()) {
            BlockPos pos = event.getPos();
            BlockState state = event.getState();
            if (state.hasProperty(JukeboxBlock.HAS_RECORD) && state.get(JukeboxBlock.HAS_RECORD) && event.getRandom().nextInt(2) == 0) {
                event.getWorld().addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5D, (double)pos.getY() + 1.2D, (double)pos.getZ() + 0.5D, (double)event.getRandom().nextInt(25) / 24.0D, 0.0D, 0.0D);
            }
        }
    }
}
