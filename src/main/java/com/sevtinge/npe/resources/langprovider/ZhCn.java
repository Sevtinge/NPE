package com.sevtinge.npe.resources.langprovider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ZhCn extends FabricLanguageProvider {
    public ZhCn(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "zh_cn", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider holderLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add("item.npe.null_pointer_exception", "java.lang.NullPointerException");

        translationBuilder.add("advancement.npe.get_null_pointer_exception", "锟斤拷烫烫烫");
        translationBuilder.add("advancement.npe.get_null_pointer_exception_desc", "数据世界的尽头。崩坏常伴吾身。");
        translationBuilder.add("advancement.npe.use_null_pointer_exception", "这是特性，不是bug");
        translationBuilder.add("advancement.npe.use_null_pointer_exception_desc", "throw new NullPointerException();");

        translationBuilder.add("others.npe.throw_msg", "刻意的游戏设计");
    }
}