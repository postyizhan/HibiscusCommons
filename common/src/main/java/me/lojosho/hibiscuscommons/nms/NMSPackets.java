package me.lojosho.hibiscuscommons.nms;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public interface NMSPackets {

    void sendSlotUpdate(
            Player player,
            int slot
    );

    void sendEquipmentSlotUpdate(
            int entityId,
            org.bukkit.inventory.EquipmentSlot slot,
            ItemStack item,
            List<Player> sendTo
    );

    void sendEquipmentSlotUpdate(
            int entityId,
            HashMap<EquipmentSlot, ItemStack> equipment,
            List<Player> sendTo
    );

    void sendScoreboardHideNamePacket(
            Player player,
            String name
    );

}
