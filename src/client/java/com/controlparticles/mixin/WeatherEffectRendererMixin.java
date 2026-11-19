package com.controlparticles.mixin;

import com.controlparticles.ConfigManager;
import com.controlparticles.Settings;
import com.controlparticles.Visibility;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.client.renderer.state.level.WeatherRenderState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** The falling rain / snow streaks themselves. */
@Mixin(WeatherEffectRenderer.class)
public abstract class WeatherEffectRendererMixin {
	@Inject(method = "extractRenderState", at = @At("RETURN"))
	private void controlparticles$thin(ClientLevel level, float partialTicks, Vec3 cameraPos, WeatherRenderState state, CallbackInfo ci) {
		Settings s = ConfigManager.settings();
		thinOut(state.rainColumns, s.rainStreaks);
		thinOut(state.snowColumns, s.snowStreaks);
	}

	@org.spongepowered.asm.mixin.Unique
	private static void thinOut(List<?> columns, Visibility v) {
		if (v == Visibility.HIDE) {
			columns.clear();
		} else if (v == Visibility.REDUCED) {
			// keep a fixed quarter of the columns so the rain thins out without flickering
			int i = 0;
			for (Iterator<?> it = columns.iterator(); it.hasNext(); i++) {
				it.next();
				if (i % 4 != 0) it.remove();
			}
		}
	}
}
