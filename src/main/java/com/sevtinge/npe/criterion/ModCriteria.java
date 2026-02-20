package com.sevtinge.npe.criterion;

import com.sevtinge.npe.NPE;
import net.minecraft.advancements.CriteriaTriggers;

public class ModCriteria {
    public static final ReleaseItemCriterion RELEASE_ITEM = CriteriaTriggers.register(NPE.MOD_ID + ":release_item", new ReleaseItemCriterion());

    public static void initialize() {}
}
