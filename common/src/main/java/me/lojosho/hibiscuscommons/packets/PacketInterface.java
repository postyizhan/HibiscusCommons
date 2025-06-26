package me.lojosho.hibiscuscommons.packets;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public interface PacketInterface {

    default PacketAction writeContainerContent(@NotNull Player player, Integer windowId, @NotNull List<ItemStack> slotData) {
        return PacketAction.NOTHING;
    }

    default PacketAction writeSlotContent(@NotNull Player player, Integer windowId, Integer slot, @NotNull ItemStack itemStack) {
        return PacketAction.NOTHING;
    }

    default PacketAction writeEquipmentContent(@NotNull Player player, @NotNull Map<EquipmentSlot, ItemStack> armor) {
        return PacketAction.NOTHING;
    }

    default PacketAction writePassengerContent(@NotNull Player player, Integer owner, List<Integer> passengers) {
        return PacketAction.NOTHING;
    }

    default PacketAction readInventoryClick(@NotNull Player player, Integer clickType, Integer slotNumber) {
        return PacketAction.NOTHING;
        // Override
    }

    default PacketAction readPlayerAction(@NotNull Player player, Integer actionType) {
        return PacketAction.NOTHING;
        // Override
    }

    default PacketAction readPlayerArm(@NotNull Player player) {
        return PacketAction.NOTHING;
        // Override
    }

    default PacketAction readEntityHandle(@NotNull Player player) {
        return PacketAction.NOTHING;
        // Override
    }
}
