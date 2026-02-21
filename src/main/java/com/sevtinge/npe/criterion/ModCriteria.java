package com.sevtinge.npe.criterion;

import com.sevtinge.npe.Main;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCriteria {

/*
    public static ReleaseItemCriterion RELEASE_ITEM;

    public static void initialize() {
        // 在 FMLCommonSetupEvent 或注册事件里调用
        RELEASE_ITEM = CriteriaTriggers.register(
                Main.MODID + ":release_item",
                new ReleaseItemCriterion()
        );
    }
*/

    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, Main.MODID);

    public static final Supplier<ReleaseItemCriterion> RELEASE_ITEM = register("release_item", ReleaseItemCriterion::new);

    private static <T extends CriterionTrigger<?>> DeferredHolder<CriterionTrigger<?>, T> register(String id, Supplier<T> trigger) {
        return TRIGGERS.register(id, trigger);
    }

    public static void register(IEventBus eventBus) {
        TRIGGERS.register(eventBus);
    }
}
