package me.lojosho.hibiscuscommons.nms;

import lombok.Getter;
import me.lojosho.hibiscuscommons.HibiscusCommonsPlugin;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

public class NMSHandlers {

    private static final LinkedHashMap<MinecraftVersion, String> VERSION_MAP = new LinkedHashMap <>() {{
        put(MinecraftVersion.v1_20_4, "v1_20_R3");
        // 1.20.5 is not supported; was imminently bumped to 1.20.6
        put(MinecraftVersion.v1_20_6, "v1_20_R4");
        // 1.20 is not supported; was imminently bumped to 1.21.1
        put(MinecraftVersion.v1_21_1, "v1_21_R1");
        // 1.20.2 is not supported; was imminently bumped to 1.21.3
        put(MinecraftVersion.v1_21_3, "v1_21_R2");
        put(MinecraftVersion.v1_21_4, "v1_21_R3");
    }};

    private static NMSHandler handler;
    @Getter
    private static MinecraftVersion version;

    public static boolean isVersionSupported() {
        return getVersion() != null;
    }

    public static NMSHandler getHandler() {
        if (handler == null) setup();
        return handler;
    }

    public static void setup() throws RuntimeException {
        if (handler != null) return;
        final String bukkitVersion = Bukkit.getServer().getBukkitVersion();
        String minecraftVersion = bukkitVersion.substring(0, bukkitVersion.indexOf('-'));
        MinecraftVersion enumVersion = MinecraftVersion.fromVersionString(minecraftVersion);
        String packageVersion = VERSION_MAP.get(enumVersion);

        if (packageVersion == null) {
            HibiscusCommonsPlugin.getInstance().getLogger().severe("An error occurred while trying to detect the version of the server.");
            HibiscusCommonsPlugin.getInstance().getLogger().severe(" ");
            HibiscusCommonsPlugin.getInstance().getLogger().severe("Detected Bukkit Version: " + bukkitVersion);
            HibiscusCommonsPlugin.getInstance().getLogger().severe("Detected Minecraft Version: " + minecraftVersion);
            HibiscusCommonsPlugin.getInstance().getLogger().severe("Detected Package Version: " + packageVersion);
            HibiscusCommonsPlugin.getInstance().getLogger().severe(" ");
            HibiscusCommonsPlugin.getInstance().getLogger().severe("Supported versions:");
            for (MinecraftVersion supportedVersion : VERSION_MAP.keySet()) {
                HibiscusCommonsPlugin.getInstance().getLogger().severe("  - " + supportedVersion.toVersionString());
            }
            HibiscusCommonsPlugin.getInstance().getLogger().severe(" ");
            HibiscusCommonsPlugin.getInstance().getLogger().severe("Please update HibiscusCommons that supports this version.");
            throw new RuntimeException("Failed to detect the server version.");
        }

        for (String selectedVersion : VERSION_MAP.values()) {
            if (!selectedVersion.contains(packageVersion)) {
                continue;
            }
            //MessagesUtil.sendDebugMessages(packageVersion + " has been detected.", Level.INFO);
            version = enumVersion;
            try {
                NMSUtils utilHandler = (NMSUtils) Class.forName("me.lojosho.hibiscuscommons.nms." + packageVersion + ".NMSUtils").getConstructor().newInstance();
                NMSPackets packetHandler = (NMSPackets) Class.forName("me.lojosho.hibiscuscommons.nms." + packageVersion + ".NMSPackets").getConstructor().newInstance();
                handler = new NMSHandler(utilHandler, packetHandler);
                return;
            } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
                     IllegalAccessException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
