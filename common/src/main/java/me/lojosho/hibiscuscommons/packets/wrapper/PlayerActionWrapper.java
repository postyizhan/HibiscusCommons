package me.lojosho.hibiscuscommons.packets.wrapper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PlayerActionWrapper {

    private String actionType;

    public PlayerActionWrapper(String actionType) {
        this.actionType = actionType;
    }
}
