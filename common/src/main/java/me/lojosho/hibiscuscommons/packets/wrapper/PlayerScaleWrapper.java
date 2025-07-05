package me.lojosho.hibiscuscommons.packets.wrapper;

import lombok.Getter;

public class PlayerScaleWrapper {

    @Getter
    private final double scale;

    public PlayerScaleWrapper(double scale) {
        this.scale = scale;
    }
}
