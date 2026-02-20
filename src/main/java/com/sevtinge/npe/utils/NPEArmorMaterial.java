package com.sevtinge.npe.utils;

import com.sevtinge.npe.NPE;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class NPEArmorMaterial {
    public static final TagKey<Item> REPAIRS_NPE_ARMOR = TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.fromNamespaceAndPath(NPE.MOD_ID, "repairs_npe_armor"));
}
