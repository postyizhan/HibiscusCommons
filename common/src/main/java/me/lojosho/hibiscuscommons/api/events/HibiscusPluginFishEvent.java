package me.lojosho.hibiscuscommons.api.events;

import me.lojosho.hibiscuscommons.hooks.Hook;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HibiscusPluginFishEvent extends HibiscusHookPlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private final ItemStack itemStack;

    public HibiscusPluginFishEvent(@NotNull Hook hook, @NotNull Player who, @NotNull ItemStack itemStack) {
        super(hook, who);
        this.itemStack = itemStack;
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
