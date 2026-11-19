package com.controlparticles.screen;

import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class WeatherScreen extends BaseScreen {
	public WeatherScreen(Screen last, Options options) {
		super(last, options, "Rain & snow");
	}

	@Override
	protected void addOptions() {
		list.addHeader(Component.literal("Falling streaks"));
		list.addBig(visibility("Rain (falling drops)", "The rain streaks falling from the sky.", settings().rainStreaks, v -> settings().rainStreaks = v));
		list.addBig(visibility("Snow (falling flakes)", "The snow flakes falling from the sky.", settings().snowStreaks, v -> settings().snowStreaks = v));
		list.addHeader(Component.literal("Ground particles"));
		list.addBig(mapEntry("Rain splashes", "The splashes where rain hits the ground.", settings().categories, "rain_splash"));
	}
}
