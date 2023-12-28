package me.lojosho.hibiscuscommons.api.events;

import me.lojosho.hibiscuscommons.hooks.Hook;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public abstract class HibiscusHookEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Hook hook;

    public HibiscusHookEvent(Hook hook) {
        this.hook = hook;
    }

    public Hook getHook() {
        return hook;
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
}
