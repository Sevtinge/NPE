package com.sevtinge.npe.datagenerator;

import com.sevtinge.npe.resources.langprovider.EnUs;
import com.sevtinge.npe.resources.langprovider.ZhCn;
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
