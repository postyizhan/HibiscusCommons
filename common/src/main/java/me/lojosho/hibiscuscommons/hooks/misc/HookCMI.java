package me.lojosho.hibiscuscommons.hooks.misc;

import com.Zrips.CMI.events.CMIPlayerUnVanishEvent;
import com.Zrips.CMI.events.CMIPlayerVanishEvent;
import me.lojosho.hibiscuscommons.api.events.HibiscusPlayerUnVanishEvent;
import me.lojosho.hibiscuscommons.api.events.HibiscusPlayerVanishEvent;
import me.lojosho.hibiscuscommons.hooks.Hook;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.jetbrains.annotations.NotNull;

/**
 * A hook that integrates the plugin {@link com.Zrips.CMI.CMI CMI}
 */
public class HookCMI extends Hook {
    public HookCMI() {
        super("CMI");
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerVanish(@NotNull CMIPlayerVanishEvent event) {
        HibiscusPlayerVanishEvent newEvent = new HibiscusPlayerVanishEvent(event.getPlayer());
        Bukkit.getPluginManager().callEvent(newEvent);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerShow(@NotNull CMIPlayerUnVanishEvent event) {
        HibiscusPlayerUnVanishEvent newEvent = new HibiscusPlayerUnVanishEvent(event.getPlayer());
        Bukkit.getPluginManager().callEvent(newEvent);
    }
}
