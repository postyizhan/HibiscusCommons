package me.lojosho.hibiscuscommons.util;

import org.jetbrains.annotations.NotNull;

public class StringUtils {

    @NotNull
    public static String parseStringToString(final String parsed) {
        return Adventure.SERIALIZER.serialize(Adventure.MINI_MESSAGE.deserialize(parsed));
    }

}
