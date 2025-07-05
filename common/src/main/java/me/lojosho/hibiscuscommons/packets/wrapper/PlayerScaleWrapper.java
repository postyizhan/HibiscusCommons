package me.lojosho.hibiscuscommons.packets.wrapper;

import lombok.Getter;

public class PlayerScaleWrapper {

    @Getter
    private final int entityId;
    @Getter
    private final double scale;

    public PlayerScaleWrapper(int entityId, double scale) {
        this.entityId = entityId;
        this.scale = scale;
    }
}
