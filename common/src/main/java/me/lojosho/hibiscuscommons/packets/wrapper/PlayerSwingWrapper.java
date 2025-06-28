package me.lojosho.hibiscuscommons.packets.wrapper;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public class PlayerSwingWrapper {

    @Getter
    private final String hand;

    public PlayerSwingWrapper(@NotNull String hand) {
        this.hand = hand;
    }

}
