package com.sevtinge.npe;

import com.sevtinge.npe.criterion.ModCriteria;
import net.fabricmc.api.ClientModInitializer;

public class NPEClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModItems.initialize();
		ModCriteria.initialize();
	}
}