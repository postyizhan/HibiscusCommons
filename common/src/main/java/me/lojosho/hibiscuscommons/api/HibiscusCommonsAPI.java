package me.lojosho.hibiscuscommons.api;

import me.lojosho.hibiscuscommons.HibiscusCommonsPlugin;
import org.jetbrains.annotations.NotNull;

public class HibiscusCommonsAPI {

    /**
     * Returns the version of HibiscusCommons
     * @return
     */
    @NotNull
    public static String getHibiscusVersion() {
        return HibiscusCommonsPlugin.getInstance().getDescription().getVersion();
    }

}
