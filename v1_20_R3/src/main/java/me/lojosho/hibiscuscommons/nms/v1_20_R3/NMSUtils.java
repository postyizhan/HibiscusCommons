package me.lojosho.hibiscuscommons.nms.v1_20_R3;

import net.minecraft.server.level.ServerLevel;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.craftbukkit.v1_20_R3.CraftServer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
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
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return null;

        Color color = null;
        switch (meta) {
            case LeatherArmorMeta leatherMeta -> color = leatherMeta.getColor();
            case PotionMeta potionMeta -> color = potionMeta.getColor();
            case MapMeta mapMeta -> color = mapMeta.getColor();
            case FireworkEffectMeta fireworkEffectMeta -> {
                FireworkEffect effect = fireworkEffectMeta.getEffect();
                if (effect != null) {
                    color = effect.getColors().stream().findFirst().isPresent() ? effect.getColors().stream().findFirst().get() : null;
                }
            }
            default -> {}
        }

        return color;
    }

    @Override
    public ItemStack setColor(@NotNull ItemStack itemStack, Color color) {
        ItemMeta meta = itemStack.getItemMeta();
        switch (meta) {
            case LeatherArmorMeta leatherMeta -> leatherMeta.setColor(color);
            case PotionMeta potionMeta -> potionMeta.setColor(color);
            case MapMeta mapMeta -> mapMeta.setColor(color);
            case FireworkEffectMeta fireworkMeta -> fireworkMeta.setEffect(
                    FireworkEffect.builder()
                            .with(FireworkEffect.Type.BALL)
                            .withColor(color)
                            .trail(false)
                            .flicker(false)
                            .build()
            );
            case null, default -> {}
        }

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private net.minecraft.world.entity.Entity getNMSEntity(int entityId) {
        for (ServerLevel world : ((CraftServer) Bukkit.getServer()).getHandle().getServer().getAllLevels()) {
            net.minecraft.world.entity.Entity entity = world.getEntity(entityId);
            if (entity == null) continue;
            return entity;
        }
        return null;
    }
}
