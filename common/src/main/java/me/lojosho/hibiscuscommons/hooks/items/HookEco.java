package me.lojosho.hibiscuscommons.hooks.items;

import com.willfp.eco.core.items.Items;
import me.lojosho.hibiscuscommons.hooks.Hook;
import me.lojosho.hibiscuscommons.hooks.HookFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HookEco extends Hook {
    public HookEco() {
        super("Eco", HookFlag.ITEM_SUPPORT);
    }

    @Override
    public ItemStack getItem(@NotNull String itemId) {
        return Items.lookup(itemId).getItem();
    }
}
