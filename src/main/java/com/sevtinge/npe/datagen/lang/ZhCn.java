package com.sevtinge.npe.datagen.lang;

import com.sevtinge.npe.Main;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ZhCn extends LanguageProvider {
    public ZhCn(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }
    @Override
    protected void addTranslations() {
        this.add(Main.NULL_POINTER_EXCEPTION.get(), "java.lang.NullPointerException");

        this.add("advancement.npe.get_null_pointer_exception", "锟斤拷烫烫烫");
        this.add("advancement.npe.get_null_pointer_exception_desc", "数据世界的尽头。崩坏常伴吾身。");
        this.add("advancement.npe.use_null_pointer_exception", "这是特性，不是bug");
        this.add("advancement.npe.use_null_pointer_exception_desc", "throw new NullPointerException();");

        this.add("others.npe.throw_msg", "刻意的游戏设计");
    }
}
