package me.lojosho.hibiscuscommons.hooks.misc;

import de.myzelyam.api.vanish.PlayerHideEvent;
import de.myzelyam.api.vanish.PlayerShowEvent;
import de.myzelyam.api.vanish.VanishAPI;
import me.lojosho.hibiscuscommons.api.events.HibiscusPlayerUnVanishEvent;
import me.lojosho.hibiscuscommons.api.events.HibiscusPlayerVanishEvent;
import me.lojosho.hibiscuscommons.hooks.Hook;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * A hook that integrates the plugin {@link de.myzelyam.api.vanish.VanishAPI Supervanish}
 *
 * @implSpec Supervanish and Premium Vanish both use the same api
 */
public class HookPremiumVanish extends Hook {
    public HookPremiumVanish() {
        super("PremiumVanish");
        setActive(true);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerVanish(@NotNull PlayerHideEvent event) {
        HibiscusPlayerVanishEvent newEvent = new HibiscusPlayerVanishEvent(this, event.getPlayer());
        Bukkit.getPluginManager().callEvent(newEvent);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerShow(@NotNull PlayerShowEvent event) {
        HibiscusPlayerUnVanishEvent newEvent = new HibiscusPlayerUnVanishEvent(this, event.getPlayer());
        Bukkit.getPluginManager().callEvent(newEvent);
    }

    @Override
    public boolean isInvisible(UUID uuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if (!offlinePlayer.isOnline()) return false;
        Player player = offlinePlayer.getPlayer();
        if (player == null) return false;
        return VanishAPI.isInvisible(player);
    }
}
