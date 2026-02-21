package com.sevtinge.npe;

import com.sevtinge.npe.criterion.ModCriteria;
import com.sevtinge.npe.datagen.NPEDataGeneratorHandler;
import com.sevtinge.npe.item.NullPointerExceptionItem;
import com.sevtinge.npe.utils.NPEArmorMaterial;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.extensions.IItemExtension;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraft.client.Minecraft;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Main.MODID)
public class Main {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "npe";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    // public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    /*public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);
*/
    // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
    /*public static final DeferredItem<Item> NULL_POINTER_EXCEPTION = ITEMS.registerSimpleItem("null_pointer_exception", new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));*/

    public static final Tier EXCEPTION_MATERIAL = new SimpleTier(
            NullPointerExceptionItem.INCORRECT_BLOCKS_FOR_DROPS,
            NullPointerExceptionItem.DURABILITY,
            NullPointerExceptionItem.SPEED,
            NullPointerExceptionItem.ATTACK_DAMAGE_BONUS,
            NullPointerExceptionItem.ENCHANTMENT_VALUE,
            () -> Ingredient.of(NullPointerExceptionItem.REPAIR_ITEMS)
    );

    public static final Supplier<NullPointerExceptionItem> NULL_POINTER_EXCEPTION = ITEMS.register(
            "null_pointer_exception",
            () -> new NullPointerExceptionItem(
                    new Item.Properties().attributes(
                            SwordItem.createAttributes(
                                    EXCEPTION_MATERIAL, 0f, NullPointerExceptionItem.ATTACK_SPEED
                            )
                    ).rarity(Rarity.EPIC).stacksTo(1)
            )
            /*() -> new NullPointerExceptionItem(
                    new Item.Properties().attributes(
                            NullPointerExceptionItem.createAttributes()
                                    .withModifierAdded(
                                            Attributes.ATTACK_DAMAGE,
                                            new AttributeModifier(
                                                    NullPointerExceptionItem.BASE_ATTACK_DAMAGE_ID,
                                                    13.0,
                                                    AttributeModifier.Operation.ADD_VALUE
                                            ),
                                            EquipmentSlotGroup.MAINHAND
                                    )
                                    .withModifierAdded(
                                            Attributes.ATTACK_SPEED,
                                            new AttributeModifier(
                                                    NullPointerExceptionItem.BASE_ATTACK_SPEED_ID,
                                                    -2.8,
                                                    AttributeModifier.Operation.ADD_VALUE
                                            ),
                                            EquipmentSlotGroup.MAINHAND
                                    )
                    )
            )*/
    );

    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    /*public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("item.npe.null_pointer_exception")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> NULL_POINTER_EXCEPTION.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(NULL_POINTER_EXCEPTION.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());*/

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Main(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        //BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        //CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        ModCriteria.register(modEventBus);
        NPEDataGeneratorHandler.register(modEventBus);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(NULL_POINTER_EXCEPTION.get());
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    static class ClientModEvents {
        @SubscribeEvent
        static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
