package me.lojosho.hibiscuscommons.listener;

import me.lojosho.hibiscuscommons.nms.NMSHandlers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerConnectionEvent implements Listener {

    @EventHandler(ignoreCancelled = false, priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent event) {
        NMSHandlers.getHandler().getUtilHandler().handleChannelOpen(event.getPlayer());
    }
}
