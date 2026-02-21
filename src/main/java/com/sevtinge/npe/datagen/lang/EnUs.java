package com.sevtinge.npe.datagen.lang;

import com.sevtinge.npe.Main;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class EnUs extends LanguageProvider {
    public EnUs(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }
    @Override
    protected void addTranslations() {
        this.add(Main.NULL_POINTER_EXCEPTION.get(), "java.lang.NullPointerException");

        this.add("advancement.npe.get_null_pointer_exception", "NullPointerException");
        this.add("advancement.npe.get_null_pointer_exception_desc", "At the edge of the data realm, corruption follows me wherever I go.");
        this.add("advancement.npe.use_null_pointer_exception", "How could it be?");
        this.add("advancement.npe.use_null_pointer_exception_desc", "throw new NullPointerException();");

        this.add("others.npe.throw_msg", "Intentional Game Design");
    }
}
