package me.lojosho.hibiscuscommons.hooks.misc;

import me.lojosho.hibiscuscommons.api.events.HibiscusPluginFishEvent;
import me.lojosho.hibiscuscommons.hooks.Hook;
import net.momirealms.customfishing.api.event.FishingLootSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class HookCustomFishing extends Hook {

    public HookCustomFishing() {
        super("CustomFishing");
        setActive(true);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerFish(FishingLootSpawnEvent event) {
        if (event.getEntity() == null) return;
        if (!(event.getEntity() instanceof Item item)) return;
        HibiscusPluginFishEvent newEvent = new HibiscusPluginFishEvent(this, event.getPlayer(), item.getItemStack());
        Bukkit.getPluginManager().callEvent(newEvent);
    }
}
