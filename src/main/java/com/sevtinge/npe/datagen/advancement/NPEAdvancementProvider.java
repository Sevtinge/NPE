package com.sevtinge.npe.datagen.advancement;

import com.sevtinge.npe.Main;
import com.sevtinge.npe.criterion.ModCriteria;
import com.sevtinge.npe.criterion.ReleaseItemCriterion;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class NPEAdvancementProvider extends AdvancementProvider {
    /**
     * Constructs an advancement provider using the generators to write the
     * advancements to a file.
     *
     * @param output             the target directory of the data generator
     * @param registries         a future of a lookup for registries and their objects
     * @param existingFileHelper a helper used to find whether a file exists
     * @param subProviders       the generators used to create the advancements
     */
    public NPEAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper, List<AdvancementGenerator> subProviders) {
        super(output, registries, existingFileHelper, subProviders);
    }

    public static net.minecraft.data.advancements.AdvancementProvider create(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        return new net.minecraft.data.advancements.AdvancementProvider(packOutput, lookupProvider, List.of(new NPEAdvancementGenerator()));
    }

    public static class NPEAdvancementGenerator implements AdvancementSubProvider {
        @Override
        public void generate(HolderLookup.Provider wrapperLookup, Consumer<AdvancementHolder> consumer) {
            AdvancementHolder getNPE = Advancement.Builder.advancement()
                    .display(
                            Main.NULL_POINTER_EXCEPTION.get(), // The display icon
                            Component.translatable("advancement.npe.get_null_pointer_exception"), // The title
                            Component.translatable("advancement.npe.get_null_pointer_exception_desc"), // The description
                            ResourceLocation.withDefaultNamespace("textures/gui/advancements/backgrounds/adventure.png"), // Background image for the tab in the advancements page, if this is a root advancement (has no parent)
                            AdvancementType.CHALLENGE, // TASK, CHALLENGE, or GOAL
                            true, // Show the toast when completing it
                            true, // Announce it to chat
                            false // Hide it in the advancement tab until it's achieved
                    )
                    // "got_null_pointer_exception" is the name referenced by other advancements when they want to have "requirements."
                    .addCriterion("got_null_pointer_exception", InventoryChangeTrigger.TriggerInstance.hasItems(Main.NULL_POINTER_EXCEPTION.get()))
                    // Give the advancement an id
                    .save(consumer, Main.MODID + ":get_null_pointer_exception");

            final HolderLookup.RegistryLookup<Item> itemLookup = wrapperLookup.lookupOrThrow(Registries.ITEM);
            AdvancementHolder useNPE = Advancement.Builder.advancement()
                    .parent(getNPE)
                    .display(
                            Main.NULL_POINTER_EXCEPTION.get(),
                            Component.translatable("advancement.npe.use_null_pointer_exception"),
                            Component.translatable("advancement.npe.use_null_pointer_exception_desc"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("used_null_pointer_exception", ModCriteria.RELEASE_ITEM.get().createCriterion(new ReleaseItemCriterion.Instance(Optional.empty())))
                    .save(consumer, Main.MODID + ":use_null_pointer_exception");
        }
    }
}
