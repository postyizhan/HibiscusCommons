package me.lojosho.hibiscuscommons.api.events;

import me.lojosho.hibiscuscommons.hooks.Hook;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * This is called when a hook is reloaded from a plugin. This is useful for plugins like ItemsAdder, which loads items async from the main thread.
 */
public class HibiscusHookReload extends HibiscusHookEvent {

    private static final HandlerList handlers = new HandlerList();

    private final ReloadType reloadType;

    public HibiscusHookReload(Hook hook, ReloadType reloadType) {
        super(hook);
        this.reloadType = reloadType;
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

    public ReloadType getReloadType() {
        return reloadType;
    }

    public enum ReloadType {
        INITIAL,
        RELOAD
    }
}
