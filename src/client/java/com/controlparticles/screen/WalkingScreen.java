package com.controlparticles.screen;

import com.controlparticles.Visibility;
import com.controlparticles.WalkingStyle;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class WalkingScreen extends BaseScreen {
	public WalkingScreen(Screen last, Options options) {
		super(last, options, "Walking, breaking & blocks");
	}

	@Override
	protected void addOptions() {
		list.addHeader(Component.literal("Sprinting / walking"));
		list.addBig(visibility("Walking particles", "The puffs kicked up while sprinting. Applies to every entity.",
			settings().walking, v -> settings().walking = v));
		list.addBig(CycleButton.builder(WalkingStyle::text, settings().walkingStyle).withValues(WalkingStyle.values())
			.create(Component.literal("Walking particle style"), (b, v) -> settings().walkingStyle = v));
		list.addHeader(Component.literal("Blocks"));
		list.addBig(visibility("Block breaking particles", "Debris when a block is broken.",
			settings().blockBreaking, v -> settings().blockBreaking = v));
		list.addBig(visibility("Block hitting particles", "Chips while you mine a block.",
			settings().blockHitting, v -> settings().blockHitting = v));
	}
}
