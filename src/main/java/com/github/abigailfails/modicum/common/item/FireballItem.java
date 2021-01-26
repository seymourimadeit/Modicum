/* TODO move to crossbows mod
package com.github.abigailfails.modicum.common.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class FireballItem extends Item {
    public FireballItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        Vector3d look = player.getLookVec();
        FireballEntity fireball = new FireballEntity(world, player, look.x*0.1D, look.y*0.1D, look.z*0.1D);
        fireball.setLocationAndAngles(player.getPosX(), player.getPosYEye(), player.getPosZ(), 0,0);
        fireball.explosionPower = 1;
        world.addEntity(fireball);
        world.playEvent(null, 1016, player.getPosition(), 0);
        if (!player.isCreative()) {
            stack.shrink(1);
        }
        player.getCooldownTracker().setCooldown(this, 20);
        return ActionResult.resultSuccess(stack);
    }
}
*/
