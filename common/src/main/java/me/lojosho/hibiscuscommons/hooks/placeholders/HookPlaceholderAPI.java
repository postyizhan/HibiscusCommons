package me.lojosho.hibiscuscommons.hooks.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.lojosho.hibiscuscommons.hooks.Hook;

import java.util.ArrayList;

public class HookPlaceholderAPI extends Hook {

    private ArrayList<PlaceholderExpansion> placeHolders = new ArrayList<>();

    public HookPlaceholderAPI() {
        super("PlaceholderAPI");
        setActive(true);
    }

    public void registerPlaceholder(PlaceholderExpansion placeholderExpansion) {
        placeHolders.add(placeholderExpansion);
    }

    @Override
    public void load() {
        for (PlaceholderExpansion placeholderExpansion : placeHolders) {
            placeholderExpansion.register();
        }
    }
}
