package com.github.abigailfails.modicum.core.event;

import com.github.abigailfails.modicum.core.Modicum;
import com.github.abigailfails.modicum.core.ModicumConfig;
import com.minecraftabnormals.abnormals_core.core.events.AnimateTickEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Modicum.MOD_ID)
public class ModicumEvents {

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        ItemStack stack = event.getItemStack();
        PlayerEntity player = event.getPlayer();

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
                    event.setCancellationResult(ActionResultType.SUCCESS);
                    event.setCanceled(true);
                }
            }
        }
        if (!event.isCanceled() && ModicumConfig.COMMON.tntDropping.get() && player.isElytraFlying()) {
            boolean hasTNT = stack.getItem() == Items.TNT || player.getItemStackFromSlot(EquipmentSlotType.OFFHAND).getItem() == Items.TNT;
            boolean hasFlintAndSteel = stack.getItem() == Items.FLINT_AND_STEEL || player.getItemStackFromSlot(EquipmentSlotType.OFFHAND).getItem() == Items.FLINT_AND_STEEL;
            boolean hasFireCharge = stack.getItem() == Items.FIRE_CHARGE || player.getItemStackFromSlot(EquipmentSlotType.OFFHAND).getItem() == Items.FIRE_CHARGE;
            if (hasTNT && (hasFlintAndSteel || hasFireCharge)) {
                TNTEntity tnt = EntityType.TNT.create(event.getWorld());
                if (tnt != null) {
                    BlockPos pos = event.getPos().offset(player.getHorizontalFacing());
                    tnt.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
                    event.getWorld().addEntity(tnt);
                    event.getWorld().playSound(null, pos, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.PLAYERS, 1.0f, 1.0f);
                    if (hasFlintAndSteel) {
                        stack.damageItem(1, player, (p_219998_1_) -> p_219998_1_.sendBreakAnimation(event.getHand()));
                    } else if (!player.isCreative()) {
                        stack.shrink(1);
                    }
                    event.setCancellationResult(ActionResultType.SUCCESS);
                    event.setCanceled(true);
                }
            }
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
