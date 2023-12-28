package me.lojosho.hibiscuscommons.hooks.misc;

import me.lojosho.hibiscuscommons.api.events.HibiscusPluginFishEvent;
import me.lojosho.hibiscuscommons.hooks.Hook;
import net.momirealms.customfishing.api.event.FishingResultEvent;
import net.momirealms.customfishing.api.mechanic.loot.LootType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;

public class HookCustomFishing extends Hook {

    public HookCustomFishing() {
        super("CustomFishing");
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerFish(FishingResultEvent event) {
        // TODO: Finish this once I get a firmer response on how this api is suppose to work
        if (!event.getLoot().getType().equals(LootType.ITEM)) return;
        HibiscusPluginFishEvent newEvent = new HibiscusPluginFishEvent(this, event.getPlayer(), new ItemStack(Material.ICE));
        Bukkit.getPluginManager().callEvent(newEvent);
    }
}
