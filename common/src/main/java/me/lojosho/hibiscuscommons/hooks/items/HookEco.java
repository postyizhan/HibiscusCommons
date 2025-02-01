package me.lojosho.hibiscuscommons.hooks.items;

import com.willfp.eco.core.items.Items;
import me.lojosho.hibiscuscommons.hooks.Hook;
import me.lojosho.hibiscuscommons.hooks.HookFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HookEco extends Hook {
    public HookEco() {
        super("Eco", HookFlag.ITEM_SUPPORT);
        setActive(true);
    }

    @Override
    public ItemStack getItem(@NotNull String itemId) {
        return Items.lookup(itemId).getItem();
    }

    @Override
    public String getItemString(ItemStack itemStack) {
        if (itemStack == null) return null;
        if (!itemStack.hasItemMeta()) return null;
        if (!Items.isCustomItem(itemStack)) return null;
        // This should work? I'm not sure if it will return the correct key
        return Items.getCustomItem(itemStack).getKey().toString();
    }
}
