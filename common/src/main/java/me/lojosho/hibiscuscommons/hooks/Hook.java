package me.lojosho.hibiscuscommons.hooks;

import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Represents a hook into other minecraft plugins
 */
public abstract class Hook implements Listener {

    private final String id;

    private boolean detected = false;
    private boolean active = false;

    private boolean itemHook = false;
    private boolean entityHook = false;
    private boolean lateLoadHook = false;

    public Hook(@NotNull String id, HookFlag... flags) {
        this.id = id;
        for (HookFlag flag : flags) {
            switch (flag) {
                case ITEM_SUPPORT:
                    setEnabledItemHook(true);
                    break;
                case ENTITY_SUPPORT:
                    setEnabledEntityHook(true);
                    break;
                case LATE_LOAD:
                    setEnabledLateLoadHook(true);
                    break;
            }
        }

        Hooks.addHook(this);
    }

    /**
     * Loads this hook
     *
     * @implNote By default, this method does nothing. It should be overridden by child classes to implement any necessary loading logic
     */
    public void load() {}

    /**
     * Gets an {@link ItemStack} that is associated with the provided id from the hooked plugin
     *
     * @param itemId The id of the {@link ItemStack}
     * @return The {@link ItemStack} with the id provided. If an invalid id was provided or if the hook doesn't have any related {@link ItemStack}s then this will return null
     * @implNote By default, this method returns null. It should be overridden by child classes if you will to have your hook return a related {@link ItemStack}
     */
    @Nullable
    public ItemStack getItem(@NotNull String itemId) {
        return null;
    }

    /**
     * Gets the id of this hook
     *
     * @return The unique id for this hook
     */
    @NotNull
    public final String getId() {
        return id;
    }

    /**
     * Gets whether this hook has been activated
     *
     * @return true if this hook is active, false otherwise
     * @deprecated As of release 2.2.5+, replaced by {@link #isActive()}
     */
    @Deprecated
    public boolean getActive() {
        return this.active;
    }

    /**
     * Gets whether this hook has been activated
     *
     * @return true if this hook is active, false otherwise
     * @since 2.2.5
     */
    public final boolean isActive() {
        return this.active;
    }

    /**
     * Sets whether this hook is active
     *
     * @param active true to activate the hook, false otherwise
     */
    public final void setActive(boolean active) {
        this.active = active;

        if (hasEnabledLateLoadHook()) Hooks.checkHookLoadingStatus();
    }

    public final boolean isDetected() {
        return this.detected;
    }

    public final void setDetected(boolean detected) {
        this.detected = detected;
    }

    /**
     * Whether the method {@link #getItem(String)} should return a non-null value
     *
     * @return true if {@link #getItem(String)} should return a non-null value, false otherwise
     * @apiNote Even though this method returns true does not mean that {@link #getItem(String)} won't return null, rather if this returns false then {@link #getItem(String)} should return false everytime
     */
    public final boolean hasEnabledItemHook() {
        return itemHook;
    }

    public final boolean hasEnabledEntityHook() {
        return entityHook;
    }

    public final boolean hasEnabledLateLoadHook() {
        return lateLoadHook;
    }

    /**
     * Sets whether the method {@link #getItem(String)} should return a non-null value
     *
     * @param enabled true if {@link #getItem(String)} should return a non-null value, false otherwise
     */
    public final void setEnabledItemHook(boolean enabled) {
        itemHook = enabled;
    }

    public final void setEnabledEntityHook(boolean enabled) {
        entityHook = enabled;
    }

    public final void setEnabledLateLoadHook(boolean enabled) {
        lateLoadHook = enabled;
    }

    public String getItemString(@NotNull ItemStack itemStack) {
        return null;
    }

    public String getEntityString(@NotNull Entity entity) {
        return null;
    }
}