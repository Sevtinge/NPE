package com.sevtinge.npe.datagenerator.provider.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class EnUs extends FabricLanguageProvider {
    public EnUs(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider holderLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add("item.npe.null_pointer_exception", "java.lang.NullPointerException");

        translationBuilder.add("advancement.npe.get_null_pointer_exception", "NullPointerException");
        translationBuilder.add("advancement.npe.get_null_pointer_exception_desc", "At the edge of the data realm, corruption follows me wherever I go.");
        translationBuilder.add("advancement.npe.use_null_pointer_exception", "How could it be?");
        translationBuilder.add("advancement.npe.use_null_pointer_exception_desc", "throw new NullPointerException();");

        translationBuilder.add("others.npe.throw_msg", "Intentional Game Design");
    }
}