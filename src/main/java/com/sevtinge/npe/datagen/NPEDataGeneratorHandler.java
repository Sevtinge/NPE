package com.sevtinge.npe.datagen;

import com.sevtinge.npe.Main;
import com.sevtinge.npe.datagen.advancement.NPEAdvancementProvider;
import com.sevtinge.npe.datagen.lang.EnUs;
import com.sevtinge.npe.datagen.lang.ZhCn;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class NPEDataGeneratorHandler {

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(NPEDataGeneratorHandler::gatherData);
    }

    public static void gatherData(GatherDataEvent event){
        ExistingFileHelper efh = event.getExistingFileHelper();

        // lang
        event.getGenerator().addProvider(
                event.includeClient(),
                (DataProvider.Factory<EnUs>) pOutput -> new EnUs(pOutput,Main.MODID,"en_us")
        );
        event.getGenerator().addProvider(
                event.includeClient(),
                (DataProvider.Factory<ZhCn>) pOutput -> new ZhCn(pOutput,Main.MODID,"zh_cn")
        );

        // advancement

        event.getGenerator().addProvider(
                event.includeServer(),
                (DataProvider.Factory<net.minecraft.data.advancements.AdvancementProvider>) pOutput -> NPEAdvancementProvider.create(pOutput, event.getLookupProvider())
        );
    }
}
