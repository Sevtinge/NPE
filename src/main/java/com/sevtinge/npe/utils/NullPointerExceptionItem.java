package com.sevtinge.npe.utils;

import com.sevtinge.npe.criterion.ModCriteria;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;

public class NullPointerExceptionItem extends TridentItem {

    public NullPointerExceptionItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int i) {
        if (level.isClientSide() && livingEntity instanceof Player) {
            int j = this.getUseDuration(itemStack, livingEntity) - i;
            if (j >= 10) {
                crash(level);
            }
        } else if (!level.isClientSide() && livingEntity instanceof ServerPlayer player) {
            int j = this.getUseDuration(itemStack, livingEntity) - i;
            if (j >= 10) {
                ModCriteria.RELEASE_ITEM.trigger(player);
            }
        }
        return false;
    }

    private void crash(Level level){
        if (level.isClientSide()) {
            Component text = Component.translatable("others.npe.throw_msg");
            String content = text.getString();
            throw new NullPointerException(content);
        }
    }
}
