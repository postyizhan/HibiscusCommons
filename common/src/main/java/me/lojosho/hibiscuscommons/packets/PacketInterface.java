package me.lojosho.hibiscuscommons.packets;

import me.lojosho.hibiscuscommons.packets.wrapper.ContainerContentWrapper;
import me.lojosho.hibiscuscommons.packets.wrapper.EntityEquipmentWrapper;
import me.lojosho.hibiscuscommons.packets.wrapper.PassengerWrapper;
import me.lojosho.hibiscuscommons.packets.wrapper.SlotContentWrapper;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface PacketInterface {

    default PacketAction writeContainerContent(@NotNull Player player, ContainerContentWrapper wrapper) {
        return PacketAction.NOTHING;
    }

    default PacketAction writeSlotContent(@NotNull Player player, SlotContentWrapper wrapper) {
        return PacketAction.NOTHING;
    }

    default PacketAction writeEquipmentContent(@NotNull Player player, EntityEquipmentWrapper wrapper) {
        return PacketAction.NOTHING;
    }

    default PacketAction writePassengerContent(@NotNull Player player, PassengerWrapper wrapper) {
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
