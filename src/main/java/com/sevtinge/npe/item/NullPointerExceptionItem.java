package com.sevtinge.npe.item;

//import com.sevtinge.npe.criterion.ModCriteria;
import com.sevtinge.npe.criterion.ModCriteria;
import com.sevtinge.npe.utils.NPEArmorMaterial;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

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

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        float health = target.getHealth();
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
        target.hurt(
                attacker.damageSources().playerAttack((Player) attacker),
                damage
        );

        return super.hurtEnemy(stack, target, attacker);
    }

    private void crash(Level level){
        if (level.isClientSide()) {
            Component text = Component.translatable("others.npe.throw_msg");
            String content = text.getString();
            throw new NullPointerException(content);
        }
    }
}
