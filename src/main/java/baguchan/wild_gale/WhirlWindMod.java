package baguchan.wild_gale;


import baguchan.wild_gale.registry.ModEntities;
import baguchan.wild_gale.registry.ModItems;
import baguchan.wild_gale.registry.ModMemorys;
import baguchan.wild_gale.util.JigjawHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;

import java.util.Locale;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(WhirlWindMod.MODID)
public class WhirlWindMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "whirl_wind";

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public WhirlWindMod(IEventBus modEventBus) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        // Register the Deferred Register to the mod event bus so items get registered
        ModItems.ITEM_REGISTRY.register(modEventBus);
        ModEntities.ENTITIES_REGISTRY.register(modEventBus);

        ModMemorys.MEMORY_REGISTER.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        NeoForge.EVENT_BUS.addListener(this::serverStart);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ModItems.dispenserInit();
        PotionBrewing.addContainer(ModItems.CHARGE_POTION.get());
        PotionBrewing.addContainerRecipe(ModItems.CHARGE_POTION.get(), ModItems.WIND_CHARGE.get(), Items.SPLASH_POTION);
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(ModItems.WHIRL_WIND_SPAWNEGG.get());
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.BREEZE_ROD.get());
            event.accept(ModItems.BREEZE_POWDER.get());
        }
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.WIND_CHARGE.get());
            BuiltInRegistries.POTION.holders()
                    .filter(p_270012_ -> !p_270012_.is(Potions.EMPTY_ID))
                    .map(p_269986_ -> PotionUtils.setPotion(new ItemStack(ModItems.CHARGE_POTION.get()), p_269986_.value()))
                    .forEach(event::accept);
        }

    }

    private static void generatePotionEffectTypes(
            BuildCreativeModeTabContentsEvent p_270129_, HolderLookup<Potion> p_270334_, Item p_270968_, CreativeModeTab.TabVisibility p_270778_
    ) {
        p_270334_.listElements()
        ;
    }

    private void serverStart(final ServerAboutToStartEvent event) {

        JigjawHelper.registerJigsaw(event.getServer(),
                new ResourceLocation("minecraft:trial_chambers/spawner/contents/breeze"),
                new ResourceLocation(WhirlWindMod.MODID, "trial_chambers/whirl_wind"), 1);

    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(WhirlWindMod.MODID, name.toLowerCase(Locale.ROOT));
    }
}
