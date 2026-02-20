package com.sevtinge.npe.datagenerator;

import com.sevtinge.npe.ModItems;
import com.sevtinge.npe.NPE;
import com.sevtinge.npe.criterion.ModCriteria;
import com.sevtinge.npe.criterion.ReleaseItemCriterion;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.Optional;
import java.util.function.Consumer;

public class NPEAdvancements implements Consumer<Consumer<AdvancementHolder>> {

    private final HolderLookup.Provider wrapperLookup;

    public NPEAdvancements(HolderLookup.Provider wrapperLookup) {
        this.wrapperLookup = wrapperLookup;
    }

    @Override
    public void accept(Consumer<AdvancementHolder> consumer) {
        AdvancementHolder getNPE = Advancement.Builder.advancement()
                .display(
                        ModItems.NULL_POINTER_EXCEPTION, // The display icon
                        Component.translatable("advancement.npe.get_null_pointer_exception"), // The title
                        Component.translatable("advancement.npe.get_null_pointer_exception_desc"), // The description
                        ResourceLocation.withDefaultNamespace("textures/gui/advancements/backgrounds/adventure.png"), // Background image for the tab in the advancements page, if this is a root advancement (has no parent)
                        AdvancementType.CHALLENGE, // TASK, CHALLENGE, or GOAL
                        true, // Show the toast when completing it
                        true, // Announce it to chat
                        false // Hide it in the advancement tab until it's achieved
                )
                // "got_null_pointer_exception" is the name referenced by other advancements when they want to have "requirements."
                .addCriterion("got_null_pointer_exception", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.NULL_POINTER_EXCEPTION))
                // Give the advancement an id
                .save(consumer, NPE.MOD_ID + ":get_null_pointer_exception");

        final HolderLookup.RegistryLookup<Item> itemLookup = wrapperLookup.lookupOrThrow(Registries.ITEM);
        AdvancementHolder useNPE = Advancement.Builder.advancement()
                .parent(getNPE)
                .display(
                        ModItems.NULL_POINTER_EXCEPTION,
                        Component.translatable("advancement.npe.use_null_pointer_exception"),
                        Component.translatable("advancement.npe.use_null_pointer_exception_desc"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("used_null_pointer_exception", ModCriteria.RELEASE_ITEM.createCriterion(new ReleaseItemCriterion.Conditions(Optional.empty())))
                .save(consumer, NPE.MOD_ID + ":use_null_pointer_exception");
    }
}