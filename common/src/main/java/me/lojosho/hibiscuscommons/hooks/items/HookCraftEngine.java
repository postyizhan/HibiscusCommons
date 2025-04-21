package me.lojosho.hibiscuscommons.hooks.items;

import me.lojosho.hibiscuscommons.hooks.Hook;
import me.lojosho.hibiscuscommons.hooks.HookFlag;
import net.momirealms.craftengine.bukkit.api.CraftEngineItems;
import net.momirealms.craftengine.core.item.CustomItem;
import net.momirealms.craftengine.core.util.Key;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HookCraftEngine extends Hook {

    public HookCraftEngine() {
        super("CraftEngine", HookFlag.ITEM_SUPPORT);
    }

    @Override
    public ItemStack getItem(@NotNull String itemId) {
        Key craftEngineKey = Key.of(itemId);
        CustomItem<ItemStack> itemStack = CraftEngineItems.byId(craftEngineKey);
        if (itemStack == null) return null;
        return itemStack.buildItemStack();
    }
}