package me.lojosho.hibiscuscommons.hooks.items;

import com.denizenscript.denizen.objects.ItemTag;
import com.denizenscript.denizencore.utilities.CoreUtilities;
import me.lojosho.hibiscuscommons.hooks.Hook;
import me.lojosho.hibiscuscommons.hooks.HookFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * A hook that integrates the plugin {@link com.denizenscript.denizen.Denizen Denizen} to provide custom items
 */
@SuppressWarnings("SpellCheckingInspection")
public class HookDenizen extends Hook {
    public HookDenizen() {
        super("denizen", HookFlag.ITEM_SUPPORT);
    }

    /**
     * Gets a cosmetic {@link ItemStack} that is associated with the provided id from the plugin {@link com.denizenscript.denizen.Denizen Denizen}
     */
    @Override
    public ItemStack getItem(@NotNull String itemId) {
        ItemTag item = ItemTag.valueOf(itemId, CoreUtilities.noDebugContext);
        return item == null ? null : item.getItemStack();
    }
}
