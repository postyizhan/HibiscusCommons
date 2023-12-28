package me.lojosho.hibiscuscommons.api.events;

import me.lojosho.hibiscuscommons.hooks.Hook;
import org.bukkit.entity.Player;

public abstract class HibiscusHookPlayerEvent extends HibiscusHookEvent {

    private final Player player;

    public HibiscusHookPlayerEvent(Hook hook, Player player) {
        super(hook);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

}
