package com.sevtinge.npe.utils;

import com.sevtinge.npe.criterion.ModCriteria;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class NullPointerExceptionItem extends TridentItem {

    public static final TagKey<Block> INCORRECT_BLOCKS_FOR_DROPS = BlockTags.INCORRECT_FOR_WOODEN_TOOL;
    public static final int DURABILITY = 2048;
    public static final float SPEED = 2f;
    public static final float ATTACK_DAMAGE_BONUS = 12f;
    public static final int ENCHANTMENT_VALUE = 22;
    public static final TagKey<Item> REPAIR_ITEMS = NPEArmorMaterial.REPAIRS_NPE_ARMOR;
    public static final float ATTACK_DAMAGE = ATTACK_DAMAGE_BONUS + 1f;
    public static final float ATTACK_SPEED = -2.8f;


    public NullPointerExceptionItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (EnchantmentHelper.getTridentSpinAttackStrength(itemstack, player) > 0.0F && !player.isInWaterOrRain()) {
            return InteractionResult.FAIL;
        } else {
            player.startUsingItem(hand);
            return InteractionResult.CONSUME;
        }
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

    @Override
    public void hurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity2) {
        float health = livingEntity.getHealth();
        float damage;

        damage = ATTACK_DAMAGE + health * 0.02f;
        if (health >= 100f) {
            damage += (health - 100f) * 0.05f;
        }
        if (health >= 200f) {
            damage += (health - 150f) * 0.1f;
        }
        if (health >= 300f) {
            damage += (health - 200f) * 0.15f;
        }
        if (health >= 500f) {
            damage += (health - 300f) * 0.2f;
        }
        if (health >= 800f) {
            damage = Math.max(damage, 800f);
        }

        // 直接造成额外伤害
        livingEntity.hurt(
                livingEntity2.damageSources().playerAttack((Player) livingEntity2),
                damage
        );

        super.hurtEnemy(itemStack, livingEntity, livingEntity2);
    }

    private void crash(Level level){
        if (level.isClientSide()) {
            Component text = Component.translatable("others.npe.throw_msg");
            String content = text.getString();
            throw new NullPointerException(content);
        }
    }
}
