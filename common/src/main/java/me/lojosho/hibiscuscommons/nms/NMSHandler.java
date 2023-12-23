package me.lojosho.hibiscuscommons.nms;

import org.bukkit.entity.Entity;

public interface NMSHandler {

    int getNextEntityId();

    Entity getEntity(int entityId);

    default boolean getSupported () {
        return false;
    }

}
