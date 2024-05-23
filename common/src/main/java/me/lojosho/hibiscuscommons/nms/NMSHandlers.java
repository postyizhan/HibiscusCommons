package me.lojosho.hibiscuscommons.nms;

import lombok.Getter;
import me.lojosho.hibiscuscommons.HibiscusCommonsPlugin;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class NMSHandlers {

    private static final HashMap<String, String> VERSION_MAP = new HashMap<>() {{
        put("1.19.4", "v1_19_R3");
        put("1.20.1", "v1_20_R1");
        put("1.20.2", "v1_20_R2");
        put("1.20.4", "v1_20_R3");
        put("1.20.6", "v1_20_R4");
    }};
    private static NMSHandler handler;
    @Getter
    private static String version;

    @Nullable
    public static NMSHandler getHandler() {
        if (handler != null) {
            return handler;
        } else {
            setup();
        }
        return handler;
    }

    public static boolean isVersionSupported() {
        if (getHandler() == null) return false;
        return getHandler().getSupported();
    }

    public static void setup() {
        if (handler != null) return;
        final String bukkitVersion = Bukkit.getServer().getBukkitVersion();
        String minecraftVersion = bukkitVersion.substring(0, bukkitVersion.indexOf('-'));
        String packageVersion = VERSION_MAP.get(minecraftVersion);

        for (String selectedVersion : VERSION_MAP.values()) {
            if (!selectedVersion.contains(packageVersion)) {
                continue;
            }
            //MessagesUtil.sendDebugMessages(packageVersion + " has been detected.", Level.INFO);
            version = packageVersion;
            try {
                handler = (NMSHandler) Class.forName("me.lojosho.hibiscuscommons.nms." + packageVersion + ".NMSHandler").getConstructor().newInstance();
                return;
            } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
                     IllegalAccessException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
