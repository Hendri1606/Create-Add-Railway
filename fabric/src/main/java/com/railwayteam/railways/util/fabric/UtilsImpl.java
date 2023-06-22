package com.railwayteam.railways.util.fabric;

import com.mojang.blaze3d.platform.InputConstants;
import com.simibubi.create.AllPackets;
import com.simibubi.create.foundation.networking.SimplePacketBase;
import io.github.fabricators_of_create.porting_lib.util.KeyBindingHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

public class UtilsImpl {
	public static boolean isModLoaded(String id, @Nullable String fabricId) {
		return FabricLoader.getInstance().isModLoaded(fabricId != null ? fabricId : id);
	}

	public static Path configDir() {
		return FabricLoader.getInstance().getConfigDir();
	}

	public static boolean isDevEnv() {
		return FabricLoader.getInstance().isDevelopmentEnvironment();
	}

    @Environment(EnvType.CLIENT)
    public static boolean isActiveAndMatches(KeyMapping mapping, InputConstants.Key keyCode) {
		return KeyBindingHelper.isActiveAndMatches(mapping, keyCode);
    }

    public static void sendCreatePacketToServer(SimplePacketBase packet) {
		AllPackets.getChannel().sendToServer(packet);
    }
}
