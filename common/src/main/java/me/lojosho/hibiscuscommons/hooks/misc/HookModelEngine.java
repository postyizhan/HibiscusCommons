package me.lojosho.hibiscuscommons.hooks.misc;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ModeledEntity;
import me.lojosho.hibiscuscommons.hooks.Hook;
import org.bukkit.entity.Entity;

public class HookModelEngine extends Hook {

    public HookModelEngine() {
        super("ModelEngine");
    }

    @Override
    public String getEntityString(Entity entity) {
        ModeledEntity modeledEntity = ModelEngineAPI.getModeledEntity(entity);
        if (modeledEntity == null || modeledEntity.getModels().isEmpty()) return null;
        return modeledEntity.getModels().entrySet().stream().findFirst().get().getValue().getBlueprint().getName();
    }
}
