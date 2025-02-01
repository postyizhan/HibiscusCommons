package me.lojosho.hibiscuscommons.hooks.misc;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.events.CMIPlayerUnVanishEvent;
import com.Zrips.CMI.events.CMIPlayerVanishEvent;
import me.lojosho.hibiscuscommons.api.events.HibiscusPlayerUnVanishEvent;
import me.lojosho.hibiscuscommons.api.events.HibiscusPlayerVanishEvent;
import me.lojosho.hibiscuscommons.hooks.Hook;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * A hook that integrates the plugin {@link com.Zrips.CMI.CMI CMI}
 */
public class HookCMI extends Hook {
    public HookCMI() {
        super("CMI");
        setActive(true);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerVanish(@NotNull CMIPlayerVanishEvent event) {
        HibiscusPlayerVanishEvent newEvent = new HibiscusPlayerVanishEvent(this, event.getPlayer());
        Bukkit.getPluginManager().callEvent(newEvent);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerShow(@NotNull CMIPlayerUnVanishEvent event) {
        HibiscusPlayerUnVanishEvent newEvent = new HibiscusPlayerUnVanishEvent(this, event.getPlayer());
        Bukkit.getPluginManager().callEvent(newEvent);
    }

    @Override
    public boolean isInvisible(UUID uuid) {
        Player onlinePlayer = Bukkit.getPlayer(uuid);
        if (onlinePlayer == null) return false;
        return CMI.getInstance().getVanishManager().getAllVanished().contains(uuid);
    }
}
