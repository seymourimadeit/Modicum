package flowerfugue.modicum;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Modicum.MODID)
public class ModicumEvents {
    @SubscribeEvent
    public static void rightClickEntity(PlayerInteractEvent.EntityInteract event) {
        ItemStack stack = event.getItemStack();
        if (event.getTarget() instanceof PrimedTnt primedTnt && stack.getItem() instanceof ShearsItem) {
            primedTnt.remove(Entity.RemovalReason.DISCARDED);
        }
    }

    @SubscribeEvent
    public static void rightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        ItemStack mainHandStack = player.getMainHandItem();
        ItemStack offHandStack = player.getOffhandItem();
        ItemStack tntStack = mainHandStack.getItem() instanceof BlockItem && ((BlockItem) mainHandStack.getItem()).getBlock() instanceof TntBlock ? mainHandStack : offHandStack.getItem() instanceof BlockItem && ((BlockItem) offHandStack.getItem()).getBlock() instanceof TntBlock ? offHandStack : null;
        ItemStack ignitionSource = (mainHandStack.getItem() instanceof FlintAndSteelItem ? mainHandStack : mainHandStack.getItem() instanceof FireChargeItem ? mainHandStack : offHandStack.getItem() instanceof FlintAndSteelItem ? offHandStack : offHandStack.getItem() instanceof FireChargeItem ? offHandStack : null);
        if (tntStack != null && ignitionSource != null) {
            player.swing(event.getHand(), true);
            BlockPos pos = player.blockPosition().relative(player.getDirection());
            BlockState tntState = ((BlockItem) tntStack.getItem()).getBlock().defaultBlockState();
            tntState.onCaughtFire(player.level(), pos, null, player);
            player.level().playSound(null, pos, SoundEvents.TNT_PRIMED, SoundSource.PLAYERS, 1.0F, 1.0F);
            if (!player.isCreative()) {
                tntStack.shrink(1);
                if (ignitionSource.getItem() instanceof FlintAndSteelItem steel) {
                    ignitionSource.hurtAndBreak(1, player, (player1) -> {
                        player1.broadcastBreakEvent(event.getHand());
                        ForgeEventFactory.onPlayerDestroyItem(player1, player1.getUseItem(), event.getHand());
                    });
                } else {
                    ignitionSource.shrink(1);
                }
                player.getCooldowns().addCooldown(Items.TNT, 60);
            }
        }
    }
}
