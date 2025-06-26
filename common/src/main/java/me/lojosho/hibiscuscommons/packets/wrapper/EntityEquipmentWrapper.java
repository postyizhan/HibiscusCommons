package me.lojosho.hibiscuscommons.packets.wrapper;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Getter @Setter
public class EntityEquipmentWrapper {

    private Map<EquipmentSlot, ItemStack> armor;

    public EntityEquipmentWrapper(@NotNull Map<EquipmentSlot, ItemStack> armor) {
        this.armor = armor;
    }
}
