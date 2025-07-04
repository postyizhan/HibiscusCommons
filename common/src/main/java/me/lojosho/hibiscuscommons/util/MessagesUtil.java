package me.lojosho.hibiscuscommons.util;

import me.lojosho.hibiscuscommons.HibiscusCommonsPlugin;

import java.util.logging.Level;

public class MessagesUtil {

    private static boolean debug = true;


    public static void sendDebugMessages(String message) {
        sendDebugMessages(message, Level.INFO);
    }

    public static void sendDebugMessages(String message, Level level) {
        if (!debug) return;
        HibiscusCommonsPlugin.getInstance().getLogger().log(level, message);
    }

}
