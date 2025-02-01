package me.lojosho.hibiscuscommons.hooks.items;

import me.lojosho.hibiscuscommons.hooks.Hook;
import me.lojosho.hibiscuscommons.hooks.HookFlag;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HookMMOItems extends Hook {
    public HookMMOItems() {
        super("MMOItems", HookFlag.ITEM_SUPPORT);
        setActive(true);
    }

    @Override
    public ItemStack getItem(@NotNull String itemId) {
        String[] split = itemId.split(":", 2);
        if (split.length == 2) {
            return MMOItems.plugin.getItem(split[0], split[1]);
        }
        return null;
    }
}
