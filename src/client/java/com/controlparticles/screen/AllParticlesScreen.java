package com.controlparticles.screen;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class AllParticlesScreen extends BaseScreen {
	public AllParticlesScreen(Screen last, Options options) {
		super(last, options, "Every particle type");
	}

	@Override
	protected void addOptions() {
		list.addHeader(Component.literal("Per-type control, including modded particles"));
		List<String> ids = new ArrayList<>();
		for (Identifier id : BuiltInRegistries.PARTICLE_TYPE.keySet()) {
			ids.add(id.toString());
		}
		ids.sort(null);
		for (String id : ids) {
			list.addBig(mapEntry(pretty(id), id, settings().particles, id));
		}
	}
}
