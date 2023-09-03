package com.railwayteam.railways.config.fabric;

import com.railwayteam.railways.Railways;
import com.railwayteam.railways.config.CRConfigs;
import com.simibubi.create.foundation.config.ConfigBase;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.api.fml.event.config.ModConfigEvents;
import net.minecraftforge.fml.config.ModConfig;

import java.util.Map;

public class CRConfigsImpl {
    public static void register() {
        CRConfigs.registerCommon();

        for (Map.Entry<ModConfig.Type, ConfigBase> pair : CRConfigs.CONFIGS.entrySet())
            ModLoadingContext.registerConfig(Railways.MODID, pair.getKey(), pair.getValue().specification);

        ModConfigEvents.loading(Railways.MODID).register(CRConfigs::onLoad);
        ModConfigEvents.reloading(Railways.MODID).register(CRConfigs::onReload);
    }
}
