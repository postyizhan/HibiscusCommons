package me.lojosho.hibiscuscommons.hooks.misc;

import me.lojosho.hibiscuscommons.api.events.HibiscusPluginFishEvent;
import me.lojosho.hibiscuscommons.hooks.Hook;
import me.lojosho.hibiscuscommons.hooks.HookFlag;
import net.momirealms.customfishing.api.BukkitCustomFishingPlugin;
import net.momirealms.customfishing.api.event.FishingLootSpawnEvent;
import net.momirealms.customfishing.api.mechanic.context.Context;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HookCustomFishing extends Hook {

    public HookCustomFishing() {
        super("CustomFishing", HookFlag.ITEM_SUPPORT);
    }

    @Override
    public ItemStack getItem(@NotNull String itemId) {
        return BukkitCustomFishingPlugin.getInstance().getItemManager().buildAny(Context.player(null), itemId);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerFish(FishingLootSpawnEvent event) {
        if (event.getEntity() == null) return;
        if (!(event.getEntity() instanceof Item item)) return;
        HibiscusPluginFishEvent newEvent = new HibiscusPluginFishEvent(this, event.getPlayer(), item.getItemStack());
        Bukkit.getPluginManager().callEvent(newEvent);
    }
}
