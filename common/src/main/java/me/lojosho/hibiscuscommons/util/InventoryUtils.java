package me.lojosho.hibiscuscommons.util;

import me.lojosho.hibiscuscommons.HibiscusCommonsPlugin;
import org.bukkit.NamespacedKey;

public class InventoryUtils {


    public static NamespacedKey getOwnerKey() {
        return new NamespacedKey(HibiscusCommonsPlugin.getInstance(), "owner");
    }

    public static NamespacedKey getSkullOwner() {
        return new NamespacedKey(HibiscusCommonsPlugin.getInstance(), "skullowner");
    }

    public static NamespacedKey getSkullTexture() {
        return new NamespacedKey(HibiscusCommonsPlugin.getInstance(), "skulltexture");
    }
}
