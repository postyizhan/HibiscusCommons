package me.lojosho.hibiscuscommons.api.events;

import me.lojosho.hibiscuscommons.hooks.Hook;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * This is called when a hook is reloaded from a plugin. This is useful for plugins like ItemsAdder, which loads items async from the main thread.
 */
public class HibiscusHookReload extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Hook hook;

    public HibiscusHookReload(Hook hook) {
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
}
