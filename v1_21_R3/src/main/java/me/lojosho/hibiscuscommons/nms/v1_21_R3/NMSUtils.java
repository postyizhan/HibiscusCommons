package me.lojosho.hibiscuscommons.nms.v1_21_R3;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.component.DyedItemColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NMSUtils extends NMSCommon implements me.lojosho.hibiscuscommons.nms.NMSUtils {

    @Override
    public int getNextEntityId() {
        return net.minecraft.world.entity.Entity.nextEntityId();
    }

    @Override
    public org.bukkit.entity.Entity getEntity(int entityId) {
        net.minecraft.world.entity.Entity entity = getNMSEntity(entityId);
        if (entity == null) return null;
        return entity.getBukkitEntity();
    }

    @Override
    public @Nullable Color getColor(ItemStack itemStack) {
        if (itemStack == null) return null;
        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
        if (nmsItem == null) return null;

        DyedItemColor color = nmsItem.get(DataComponents.DYED_COLOR);
        if (color == null) return null;
        return Color.fromRGB(color.rgb());
    }

    @Override
    public ItemStack setColor(@NotNull ItemStack itemStack, Color color) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
        nmsStack.set(DataComponents.DYED_COLOR, new DyedItemColor(color.asRGB(), false));
        return CraftItemStack.asBukkitCopy(nmsStack);
    }

    private net.minecraft.world.entity.Entity getNMSEntity(int entityId) {
        for (ServerLevel world : ((CraftServer) Bukkit.getServer()).getHandle().getServer().getAllLevels()) {
            net.minecraft.world.entity.Entity entity = world.getEntity(entityId);
            if (entity == null) continue;
            return entity;
        }
        return null;
    }

    @Override
    public void handleChannelOpen(@NotNull Player player) {
        Channel channel = ((CraftPlayer) player).getHandle().connection.connection.channel;
        ChannelPipeline pipeline = channel.pipeline();

        NMSPacketChannel channelHandler = new NMSPacketChannel(player);
        for (String key : pipeline.toMap().keySet()) {
            if (!(pipeline.get(key) instanceof Connection)) continue;
            pipeline.addBefore(key, "hibiscus_channel_handler", channelHandler);
        }
    }
}
