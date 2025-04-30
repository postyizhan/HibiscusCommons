package me.lojosho.hibiscuscommons.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.lojosho.hibiscuscommons.HibiscusCommonsPlugin;
import me.lojosho.hibiscuscommons.api.events.HibiscusHooksAllActiveEvent;
import me.lojosho.hibiscuscommons.hooks.items.*;
import me.lojosho.hibiscuscommons.hooks.misc.*;
import me.lojosho.hibiscuscommons.hooks.placeholders.HookPlaceholderAPI;
import me.lojosho.hibiscuscommons.util.MessagesUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Hooks {

    private static final HashMap<String, Hook> HOOK_POOL = new HashMap<>();
    private static final HookNexo NEXO_HOOK = new HookNexo();
    private static final HookOraxen ORAXEN_HOOK = new HookOraxen();
    private static final HookItemAdder ITEMADDER_HOOK = new HookItemAdder();
    private static final HookGeary GEARY_HOOK = new HookGeary();
    private static final HookMythic MYTHIC_HOOK = new HookMythic();
    private static final HookDenizen DENIZEN_HOOK = new HookDenizen();
    private static final HookHMCColor HMC_COLOR_HOOK = new HookHMCColor();
    private static final HookCMI CMI_HOOK = new HookCMI();
    private static final HookLibsDisguises LIBS_DISGUISES_HOOK = new HookLibsDisguises();
    private static final HookModelEngine MODEL_ENGINE_HOOK = new HookModelEngine();
    private static final HookMMOItems MMO_ITEMS_HOOK = new HookMMOItems();
    private static final HookEco ECO_ITEMS_HOOK = new HookEco();
    private static final HookPlaceholderAPI PAPI_HOOK = new HookPlaceholderAPI();
    private static final HookCustomFishing CF_HOOK = new HookCustomFishing();
    private static final HookGSit GSIT_HOOK = new HookGSit();
    private static final HookCraftEngine CRAFT_ENGINE_HOOK = new HookCraftEngine();
    private static final HookPremiumVanish PREMIUM_VANISH_HOOK = new HookPremiumVanish();
    private static final HookSuperVanish SUPER_VANISH_HOOK = new HookSuperVanish();

    private static boolean allHooksActive = false;

    public static Hook getHook(@NotNull String id) {
        return HOOK_POOL.get(id.toLowerCase());
    }

    public static boolean isItemHook(@NotNull String id) {
        return HOOK_POOL.containsKey(id.toLowerCase());
    }

    public static void addHook(@NotNull Hook hook) {
        HOOK_POOL.put(hook.getId().toLowerCase(), hook);
    }

    public static void addPlaceholderAPI(@NotNull PlaceholderExpansion expansion) {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            HookPlaceholderAPI hook = (HookPlaceholderAPI) getHook("PlaceholderAPI");
            hook.registerPlaceholder(expansion);
        }
    }

    @NotNull
    public static String processPlaceholders(OfflinePlayer player, String raw) {
        if (isActiveHook("PlaceholderAPI")) return PlaceholderAPI.setPlaceholders(player, raw);
        return raw;
    }

    public static void setup() {
        for (Hook hook : HOOK_POOL.values()) {
            if (Bukkit.getPluginManager().getPlugin(hook.getId()) != null) {
                HibiscusCommonsPlugin.getInstance().getServer().getPluginManager().registerEvents(hook, HibiscusCommonsPlugin.getInstance());
                hook.setDetected(true);
                hook.load();

                HibiscusCommonsPlugin.getInstance().getLogger().info("Successfully hooked into " + hook.getId());
            }
        }

        checkHookLoadingStatus();
    }

    /**
     * Checks if all hooked hooks are actually active
     * so {@link HibiscusHooksAllActiveEvent} is called.
     * This is an operation that occurs only once to allow plugins
     * load their stuff successfully when all hooks are active.
     */
    public static void checkHookLoadingStatus() {
        if (allHooksActive) {
            return;
        }

        List<Hook> lateLoadHooks = HOOK_POOL.values().stream().filter(Hook::isDetected).filter(Hook::hasEnabledLateLoadHook).toList();
        if (lateLoadHooks.isEmpty()) {
            MessagesUtil.sendDebugMessages("Not awaiting anymore plugins... All hooks are now active.");
            setAllHooksActive();
            return;
        }

        List<Hook> activeLateHooks = lateLoadHooks.stream().filter(Hook::isActive).toList();
        if (activeLateHooks.size() == lateLoadHooks.size()) {
            MessagesUtil.sendDebugMessages("Match Hook");
            setAllHooksActive();
        }
    }

    private static void setAllHooksActive() {
        allHooksActive = true;
        Bukkit.getPluginManager().callEvent(new HibiscusHooksAllActiveEvent());
    }

    @Nullable
    public static ItemStack getItem(@NotNull String raw) {
        if (!raw.contains(":")) {
            Material mat = Material.getMaterial(raw.toUpperCase());
            if (mat == null) return null;
            return new ItemStack(mat);
        }
        // Ex. Oraxen:BigSword
        // split[0] is the plugin name
        // split[1] is the item name
        String[] split = raw.split(":", 2);

        if (!isItemHook(split[0])) return null;
        Hook hook = getHook(split[0]);
        if (!hook.hasEnabledItemHook()) return null;
        if (!hook.isDetected() || !hook.isActive()) return null;
        return hook.getItem(split[1]);
    }

    public static String getStringItem(@NotNull ItemStack itemStack) {
        for (Hook hook : HOOK_POOL.values()) {
            if (hook.isDetected() && hook.hasEnabledItemHook()) {
                String stringyItem = hook.getItemString(itemStack);
                if (stringyItem == null) continue;
                return hook.getId() + ":" + stringyItem;
            }
        }
        return itemStack.getType().toString();
    }

    public static String getStringEntity(@NotNull Entity entity) {
        for (Hook hook : HOOK_POOL.values()) {
            if (hook.isDetected() && hook.hasEnabledEntityHook()) {
                String stringyEntity = hook.getEntityString(entity);
                if (stringyEntity != null) return hook.getId() + ":" + stringyEntity;
            }
        }

        return entity.getType().toString().toUpperCase();
    }

    public static boolean isActiveHook(@NotNull String id) {
        Hook hook = getHook(id);
        if (hook == null) return false;
        if (!hook.isDetected()) return false;
        return hook.isActive();
    }

    public static boolean isInvisible(@NotNull UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) return false;
        return isInvisible(player);
    }

    public static boolean isInvisible(@NotNull Player player) {
        return player.getMetadata("vanished").getFirst().asBoolean();
    }
}
