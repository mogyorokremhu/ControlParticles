package com.controlparticles.screen;

import com.controlparticles.Catalog;
import java.util.Map;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;

public class EffectsScreen extends BaseScreen {
	public EffectsScreen(Screen last, Options options) {
		super(last, options, "Potion & effect particles");
	}

	@Override
	protected void addOptions() {
		list.addHeader(Component.literal("Groups"));
		for (Catalog.EffectGroup g : Catalog.EFFECT_GROUPS) {
			list.addBig(mapEntry(g.name(), "Hides the particles of every effect in this group.", settings().effectGroups, g.id()));
		}
		list.addHeader(Component.literal("Individual effects"));
		for (Map.Entry<String, MobEffect> e : Catalog.allEffects().entrySet()) {
			list.addBig(mapEntry(e.getValue().getDisplayName().getString(), "Particles shown around entities with " + pretty(e.getKey()) + ".",
				settings().effects, e.getKey()));
		}
	}
}
