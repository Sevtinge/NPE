package com.sevtinge.npe.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;

import java.util.Optional;

public class ReleaseItemCriterion extends SimpleCriterionTrigger<ReleaseItemCriterion.Instance> {

    public void trigger(ServerPlayer pPlayer) {
        this.trigger(pPlayer,instance -> true);
    }

    @Override
    public Codec<Instance> codec() {
        return Instance.CODEC;
    }
    // 对应的Instance类
    public static class Instance implements SimpleCriterionTrigger.SimpleInstance {
        // 存储player
        private Optional<ContextAwarePredicate> player;
        public Instance(Optional<ContextAwarePredicate> player) {
            super();
            ///
            this.player = player;
        }

        public static Codec<ReleaseItemCriterion.Instance> CODEC = ContextAwarePredicate.CODEC.optionalFieldOf("player")
                .xmap(Instance::new, Instance::player).codec();

        @Override
        public Optional<ContextAwarePredicate> player() {
            return this.player;
        }
    }
/*
    @Override
    public Codec<Conditions> codec() {
        return Conditions.CODEC;
    }

    public record Conditions(Optional<ContextAwarePredicate> playerPredicate) implements SimpleCriterionTrigger.SimpleInstance {
        public static Codec<ReleaseItemCriterion.Conditions> CODEC = ContextAwarePredicate.CODEC.optionalFieldOf("player")
                .xmap(Conditions::new, Conditions::player).codec();

        @Override
        public Optional<ContextAwarePredicate> player() {
            return playerPredicate;
        }

        public boolean requirementsMet() {
            return true; // AbstractCriterion#criterion helpfully checks the playerPredicate for us.
        }

    }

    public void trigger(ServerPlayer player) {
        trigger(player, Conditions::requirementsMet);
    }*/

}
