package com.sevtinge.npe.item;

//import com.sevtinge.npe.criterion.ModCriteria;
import com.sevtinge.npe.criterion.ModCriteria;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

public class NullPointerExceptionItem extends TridentItem {
    public NullPointerExceptionItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility ability) {
        if (ability == ItemAbilities.TRIDENT_THROW) {
            return true;
        }
        return super.canPerformAction(stack, ability);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (EnchantmentHelper.getTridentSpinAttackStrength(itemstack, player) > 0.0F && !player.isInWaterOrRain()) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @Override
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int i) {
        super.releaseUsing(itemStack, level ,livingEntity, i);
        if (level.isClientSide() && livingEntity instanceof Player) {
            int j = this.getUseDuration(itemStack, livingEntity) - i;
            if (j >= 10) {
                crash(level);
            }
        } else if (!level.isClientSide() && livingEntity instanceof ServerPlayer player) {
            int j = this.getUseDuration(itemStack, livingEntity) - i;
            if (j >= 10) {
                ModCriteria.RELEASE_ITEM.get().trigger(player);
            }
        }
    }

    private void crash(Level level){
        if (level.isClientSide()) {
            Component text = Component.translatable("others.npe.throw_msg");
            String content = text.getString();
            throw new NullPointerException(content);
        }
    }
}
