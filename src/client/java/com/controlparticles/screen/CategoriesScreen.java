package com.controlparticles.screen;

import com.controlparticles.Catalog;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class CategoriesScreen extends BaseScreen {
	public CategoriesScreen(Screen last, Options options) {
		super(last, options, "Particle categories");
	}

	@Override
	protected void addOptions() {
		list.addHeader(Component.literal("Shown / Reduced (about 1 in 4) / Hidden"));
		for (Catalog.Category c : Catalog.CATEGORIES) {
			list.addBig(mapEntry(c.name(), c.description(), settings().categories, c.id()));
		}
	}
}
