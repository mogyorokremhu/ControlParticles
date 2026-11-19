package com.controlparticles;

import com.controlparticles.screen.MainScreen;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControlParticlesClient implements ClientModInitializer {
	public static final String MOD_ID = "controlparticles";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static KeyMapping openKey;

	@Override
	public void onInitializeClient() {
		ConfigManager.load();

		openKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
			"key.controlparticles.open", InputConstants.UNKNOWN.getValue(),
			KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MOD_ID, "main"))));

		ClientTickEvents.END_CLIENT_TICK.register(mc -> {
			while (openKey.consumeClick()) {
				mc.gui.setScreen(new MainScreen(mc.gui.screen(), mc.options));
			}
		});

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, buildContext) -> dispatcher.register(
			ClientCommands.literal("controlparticles")
				.executes(ctx -> {
					Minecraft mc = Minecraft.getInstance();
					// the chat screen is still open while the command runs, so open the menu next tick
					mc.execute(() -> mc.gui.setScreen(new MainScreen(null, mc.options)));
					return 1;
				})
				.then(ClientCommands.literal("preset")
					.then(ClientCommands.literal("list").executes(ctx -> {
						StringBuilder sb = new StringBuilder();
						for (String name : ConfigManager.allPresets().keySet()) {
							if (sb.length() > 0) sb.append(", ");
							sb.append(name);
						}
						reply(ctx.getSource(), "Presets: " + sb);
						return 1;
					}))
					.then(ClientCommands.literal("apply").then(ClientCommands.argument("name", StringArgumentType.greedyString()).executes(ctx -> {
						String name = StringArgumentType.getString(ctx, "name");
						reply(ctx.getSource(), ConfigManager.applyPreset(name) ? "Applied preset " + name : "No preset called " + name);
						return 1;
					})))
					.then(ClientCommands.literal("save").then(ClientCommands.argument("name", StringArgumentType.greedyString()).executes(ctx -> {
						String name = StringArgumentType.getString(ctx, "name");
						reply(ctx.getSource(), ConfigManager.saveCustomPreset(name)
							? "Saved current settings as preset " + name : "Can't overwrite built-in preset " + name);
						return 1;
					})))
					.then(ClientCommands.literal("delete").then(ClientCommands.argument("name", StringArgumentType.greedyString()).executes(ctx -> {
						String name = StringArgumentType.getString(ctx, "name");
						reply(ctx.getSource(), ConfigManager.deleteCustomPreset(name) ? "Deleted preset " + name : "No custom preset called " + name);
						return 1;
					}))))
				.then(ClientCommands.literal("reset").executes(ctx -> {
					ConfigManager.applyPreset("Vanilla");
					reply(ctx.getSource(), "All settings reset to vanilla");
					return 1;
				}))));
	}

	private static void reply(FabricClientCommandSource source, String text) {
		source.sendFeedback(Component.literal("[Control Particles] " + text));
	}
}
