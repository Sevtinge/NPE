package com.sevtinge.npe;

import com.sevtinge.npe.utils.NullPointerExceptionItem;
import com.sevtinge.npe.utils.NPEArmorMaterial;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ToolMaterial;

import java.util.function.Function;

public final class ModItems {
    private ModItems() {}

    public static final ToolMaterial EXCEPTION_MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            2048, 2f, 12f, 22,
            NPEArmorMaterial.REPAIRS_NPE_ARMOR
    );

    public static final Item NULL_POINTER_EXCEPTION = register(
            "null_pointer_exception",
            NullPointerExceptionItem::new,
            new NullPointerExceptionItem.Properties().sword(EXCEPTION_MATERIAL, 1f, -2.8f).rarity(Rarity.EPIC));

    public static <GenericItem extends Item> GenericItem register(String name, Function<Item.Properties, GenericItem> itemFactory, Item.Properties settings) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(NPE.MOD_ID, name));

        GenericItem item = itemFactory.apply(settings.setId(itemKey));

        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register((itemGroup) -> itemGroup.accept(ModItems.NULL_POINTER_EXCEPTION));
    }
}
