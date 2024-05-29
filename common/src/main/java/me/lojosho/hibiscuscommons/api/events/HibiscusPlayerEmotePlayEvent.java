package me.lojosho.hibiscuscommons.api.events;

import lombok.Getter;
import me.lojosho.hibiscuscommons.hooks.Hook;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HibiscusPlayerEmotePlayEvent extends HibiscusHookPlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancel = false;
    @Getter
    private final String emoteName;

    public HibiscusPlayerEmotePlayEvent(Hook hook, Player player, @Nullable String emoteName) {
        super(hook, player);
        this.emoteName = emoteName;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

}
