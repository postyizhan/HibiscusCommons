package me.lojosho.hibiscuscommons.nms.v1_21_R5;

import com.mojang.datafixers.util.Pair;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import lombok.Getter;
import me.lojosho.hibiscuscommons.nms.NMSHandlers;
import me.lojosho.hibiscuscommons.packets.PacketAction;
import me.lojosho.hibiscuscommons.packets.wrapper.ContainerContentWrapper;
import me.lojosho.hibiscuscommons.packets.wrapper.EntityEquipmentWrapper;
import me.lojosho.hibiscuscommons.packets.wrapper.PassengerWrapper;
import me.lojosho.hibiscuscommons.packets.wrapper.SlotContentWrapper;
import me.lojosho.hibiscuscommons.plugins.SubPlugins;
import me.lojosho.hibiscuscommons.util.MessagesUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.CraftEquipmentSlot;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class NMSPacketChannel extends ChannelDuplexHandler {

    @Getter
    private final Player player;

    public NMSPacketChannel(Player player) {
        this.player = player;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (!(msg instanceof Packet packet)) {
            super.write(ctx, msg, promise);
            return;
        }

        switch (packet) {
            case ClientboundContainerSetContentPacket setContentPacket -> msg = handleMenuChange(setContentPacket);
            case ClientboundContainerSetSlotPacket setSlotPacket -> msg = handleSlotChange(setSlotPacket);
            case ClientboundSetEquipmentPacket equipmentPacket -> msg = handlePlayerEquipment(equipmentPacket);
            case ClientboundSetPassengersPacket passengerPacket -> msg = handlePassengerSet(passengerPacket);
            default -> {}
        }

        if (msg == null) return;
        else super.write(ctx, msg, promise);
    }

    private Packet<?> handleMenuChange(@NotNull ClientboundContainerSetContentPacket packet) {
        MessagesUtil.sendDebugMessages("Menu Initial ");

        Integer windowId = packet.containerId();
        NonNullList<ItemStack> slotData = NonNullList.create();
        slotData.addAll(packet.items());

        List<org.bukkit.inventory.ItemStack> bukkitItems = slotData.stream().map(CraftItemStack::asBukkitCopy).toList();

        AtomicReference<PacketAction> action = new AtomicReference<>(PacketAction.NOTHING);
        SubPlugins.getSubPlugins().forEach(plugin -> {

            PacketAction pluginAction = plugin.getPacketInterface().writeContainerContent(player, new ContainerContentWrapper(windowId, bukkitItems));
            if (pluginAction != PacketAction.NOTHING) action.set(pluginAction);

        });

        List<ItemStack> nmsItems = bukkitItems.stream().map(CraftItemStack::asNMSCopy).toList();

        if (action.get() == PacketAction.CANCELLED) return null;
        if (action.get() == PacketAction.NOTHING) return packet;
        return new ClientboundContainerSetContentPacket(0, packet.stateId(), nmsItems, packet.carriedItem());
    }

    private Packet<?> handleSlotChange(@NotNull ClientboundContainerSetSlotPacket packet) {
        MessagesUtil.sendDebugMessages("SetSlot Initial ");

        Integer windowId = packet.getContainerId();
        Integer slot = packet.getSlot();
        ItemStack item = packet.getItem();

        org.bukkit.inventory.ItemStack bukkitItem = CraftItemStack.asBukkitCopy(item);

        AtomicReference<PacketAction> action = new AtomicReference<>(PacketAction.NOTHING);
        SubPlugins.getSubPlugins().forEach(plugin -> {

            PacketAction pluginAction = plugin.getPacketInterface().writeSlotContent(player, new SlotContentWrapper(windowId, slot, bukkitItem));
            if (pluginAction != PacketAction.NOTHING) action.set(pluginAction);
        });

        if (action.get() == PacketAction.CANCELLED) return null;
        if (action.get() == PacketAction.NOTHING) return packet;

        final ItemStack nmsItem = CraftItemStack.asNMSCopy(bukkitItem);

        return new ClientboundContainerSetSlotPacket(0, packet.getStateId(), slot, nmsItem);
    }

    private Packet<?> handlePlayerEquipment(@NotNull ClientboundSetEquipmentPacket packet) {
        final List<Pair<net.minecraft.world.entity.EquipmentSlot, ItemStack>> nmsArmor = packet.getSlots();
        HashMap<EquipmentSlot, org.bukkit.inventory.ItemStack> bukkitArmor = new HashMap<>();
        for (Pair<net.minecraft.world.entity.EquipmentSlot, ItemStack> piece : nmsArmor) {
            EquipmentSlot slot = CraftEquipmentSlot.getSlot(piece.getFirst());
            org.bukkit.inventory.ItemStack itemStack = CraftItemStack.asBukkitCopy(piece.getSecond());
            bukkitArmor.put(slot, itemStack);
        }

        final var finalArmor = bukkitArmor;
        AtomicReference<Map<EquipmentSlot, org.bukkit.inventory.ItemStack>> armor = new AtomicReference<>(new HashMap<>());

        AtomicReference<PacketAction> action = new AtomicReference<>(PacketAction.NOTHING);
        SubPlugins.getSubPlugins().forEach(plugin -> {

            EntityEquipmentWrapper wrapper = new EntityEquipmentWrapper(finalArmor);
            PacketAction pluginAction = plugin.getPacketInterface().writeEquipmentContent(player, wrapper);
            if (pluginAction != PacketAction.NOTHING) {
                armor.set(wrapper.getArmor());
                action.set(pluginAction);
            }
        });

        if (action.get() == PacketAction.CANCELLED) return null;
        if (action.get() == PacketAction.NOTHING) return packet;

        List<Pair<net.minecraft.world.entity.EquipmentSlot, ItemStack>> newArmor = new ArrayList<>();
        for (Map.Entry<EquipmentSlot, org.bukkit.inventory.ItemStack> entry : armor.get().entrySet()) {
            net.minecraft.world.entity.EquipmentSlot slot = CraftEquipmentSlot.getNMS(entry.getKey());
            ItemStack itemStack = CraftItemStack.asNMSCopy(entry.getValue());
            newArmor.add(new Pair<>(slot, itemStack));
        }

        return new ClientboundSetEquipmentPacket(packet.getEntity(), newArmor);
    }

    private Packet<?> handlePassengerSet(@NotNull ClientboundSetPassengersPacket packet) {
        Integer ownerId = packet.getVehicle();
        List<Integer> passengers = Arrays.stream(packet.getPassengers()).boxed().collect(Collectors.toList());
        MessagesUtil.sendDebugMessages("Mount Packet Sent - Read - EntityID: " + ownerId);

        AtomicReference<PacketAction> action = new AtomicReference<>(PacketAction.NOTHING);
        SubPlugins.getSubPlugins().forEach(plugin -> {

            PacketAction pluginAction = plugin.getPacketInterface().writePassengerContent(player, new PassengerWrapper(ownerId, passengers));
            if (pluginAction != PacketAction.NOTHING) action.set(pluginAction);

        });

        if (action.get() == PacketAction.CANCELLED) return null;
        if (action.get() == PacketAction.NOTHING) return packet;
        return (Packet<?>) NMSHandlers.getHandler().getPacketHandler().createMountPacket(ownerId, passengers.stream().mapToInt(Integer::intValue).toArray());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof Packet packet)) {
            super.channelRead(ctx, msg);
            return;
        }

        switch (packet) {
            case ServerboundContainerClickPacket clickPacket -> msg = handleInventoryClick(clickPacket);
            case ServerboundPlayerActionPacket playerActionPacket -> msg = handlePlayerAction(playerActionPacket);
            case ServerboundSwingPacket swingPacket -> msg = handlePlayerArm(swingPacket);
            case ServerboundUseItemOnPacket useItemOnPacket -> msg = handleEntityUse(useItemOnPacket);
            default -> {}
        }

        if (msg == null) return;
        else super.channelRead(ctx, msg);
    }

    private Packet<?> handleInventoryClick(@NotNull ServerboundContainerClickPacket packet) {
        ClickType clickType = packet.clickType();
        int slotClicked = packet.slotNum();

        AtomicReference<PacketAction> action = new AtomicReference<>(PacketAction.NOTHING);
        SubPlugins.getSubPlugins().forEach(plugin -> {

            PacketAction pluginAction = plugin.getPacketInterface().readInventoryClick(player, clickType.id(), slotClicked);
            if (pluginAction != PacketAction.NOTHING) action.set(pluginAction);

        });
        if (action.get() == PacketAction.CANCELLED) return null;
        return packet;
    }

    private Packet<?> handlePlayerAction(ServerboundPlayerActionPacket packet) {
        ServerboundPlayerActionPacket.Action playerAction = packet.getAction();

        AtomicReference<PacketAction> action = new AtomicReference<>(PacketAction.NOTHING);
        SubPlugins.getSubPlugins().forEach(plugin -> {

            PacketAction pluginAction = plugin.getPacketInterface().readPlayerAction(player, playerAction.ordinal());
            if (pluginAction != PacketAction.NOTHING) action.set(pluginAction);

        });
        if (action.get() == PacketAction.CANCELLED) return null;
        return packet;
    }

    private Packet<?> handlePlayerArm(ServerboundSwingPacket packet) {
        AtomicReference<PacketAction> action = new AtomicReference<>(PacketAction.NOTHING);
        SubPlugins.getSubPlugins().forEach(plugin -> {

            PacketAction pluginAction = plugin.getPacketInterface().readPlayerArm(player);
            if (pluginAction != PacketAction.NOTHING) action.set(pluginAction);

        });
        if (action.get() == PacketAction.CANCELLED) return null;
        return packet;
    }

    private Packet<?> handleEntityUse(ServerboundUseItemOnPacket packet) {
        AtomicReference<PacketAction> action = new AtomicReference<>(PacketAction.NOTHING);
        SubPlugins.getSubPlugins().forEach(plugin -> {

            PacketAction pluginAction = plugin.getPacketInterface().readEntityHandle(player);
            if (pluginAction != PacketAction.NOTHING) action.set(pluginAction);

        });
        if (action.get() == PacketAction.CANCELLED) return null;
        return packet;
    }
}