package dqu.additionaladditions.mixin;

import com.google.gson.JsonElement;
import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.config.Config;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {
    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At("HEAD"))
    private void removeRecipes(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo ci) {
        HashSet<ResourceLocation> toRemove = new HashSet<>();
        Iterator<Map.Entry<ResourceLocation, JsonElement>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            ResourceLocation identifier = iterator.next().getKey();
            if (identifier.getNamespace().equals(AdditionalAdditions.namespace)) {
                switch (identifier.getPath()) {
                    case "watering_can" -> { if (!Config.get("WateringCan")) toRemove.add(identifier); }
                    case "wrench" -> { if (!Config.get("Wrench")) toRemove.add(identifier); }
                    case "crossbow_with_spyglass" -> { if (!Config.get("Crossbows")) toRemove.add(identifier); }
                    case "glow_stick" -> { if (!Config.get("GlowStick")) toRemove.add(identifier); }
                    case "depth_meter" -> { if (!Config.get("DepthMeter")) toRemove.add(identifier); }
                    case "mysterious_bundle" -> { if (!Config.get("MysteriousBundle")) toRemove.add(identifier); }
                    case "trident" -> { if (!Config.get("TridentShard")) toRemove.add(identifier); }
                    case "rope" -> { if (!Config.get("Ropes")) toRemove.add(identifier); }
                    case "copper_patina" -> { if (!Config.get("CopperPatina")) toRemove.add(identifier); }
                    case "amethyst_lamp" -> { if (!Config.get("AmethystLamp")) toRemove.add(identifier); }
                    case "honeyed_apple", "berry_pie" -> { if (!Config.get("FoodItems")) toRemove.add(identifier); }
                    case "pocket_jukebox" -> { if (!Config.get("PocketJukebox")) toRemove.add(identifier); }
                    case "powered_rails" -> { if (!Config.get("PoweredRailsCopperRecipe")) toRemove.add(identifier); }
                    default -> {
                        if (identifier.getPath().startsWith("rose_gold")) { if (!Config.get("RoseGold")) toRemove.add(identifier); }
                        if (identifier.getPath().startsWith("fried_egg")) { if (!Config.get("FoodItems")) toRemove.add(identifier); }
                        if (identifier.getPath().startsWith("gilded_netherite")) {
                            if (!Config.get("GildedNetherite")) toRemove.add(identifier);
                            if (Config.get("GoldRing")) toRemove.add(identifier);
                        }
                        if (identifier.getPath().startsWith("ring_gilded_netherite")) {
                            if (!Config.get("GildedNetherite")) toRemove.add(identifier);
                            if (!Config.get("GoldRing")) toRemove.add(identifier);
                        }
                    }
                }
            }
        }

        for (ResourceLocation identifier : toRemove) {
            map.remove(identifier);
        }
    }
}
