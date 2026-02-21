package com.sevtinge.npe.datagenerator;

import com.sevtinge.npe.datagenerator.provider.advancement.NPEAdvancementProvider;
import com.sevtinge.npe.datagenerator.provider.lang.EnUs;
import com.sevtinge.npe.datagenerator.provider.lang.ZhCn;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class NPEDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		// Advancement
		pack.addProvider(NPEAdvancementProvider::new);

		// Lang
		pack.addProvider(EnUs::new);
		pack.addProvider(ZhCn::new);
	}
}
