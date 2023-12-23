package me.lojosho.hibiscuscommons.hooks.misc;

import me.libraryaddict.disguise.events.DisguiseEvent;
import me.libraryaddict.disguise.events.UndisguiseEvent;
import me.lojosho.hibiscuscommons.api.events.HibiscusPlayerVanishEvent;
import me.lojosho.hibiscuscommons.hooks.Hook;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.jetbrains.annotations.NotNull;

public class HookLibsDisguises extends Hook {
    public HookLibsDisguises() {
        super("LibsDisguises");
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerVanish(@NotNull DisguiseEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        HibiscusPlayerVanishEvent newEvent = new HibiscusPlayerVanishEvent(player);
        Bukkit.getPluginManager().callEvent(newEvent);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerShow(@NotNull UndisguiseEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        HibiscusPlayerVanishEvent newEvent = new HibiscusPlayerVanishEvent(player);
        Bukkit.getPluginManager().callEvent(newEvent);
    }
}
