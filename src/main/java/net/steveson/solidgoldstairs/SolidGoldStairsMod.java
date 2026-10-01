package net.steveson.solidgoldstairs;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelValueEvents;
import net.steveson.solidgoldstairs.block.ModBlocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolidGoldStairsMod implements ModInitializer {
	public static final String MOD_ID = "solid_gold_stairs";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.registerModBlocks();

		FuelValueEvents.BUILD.register((builder, context) -> {
			builder.add(ModBlocks.COAL_STAIRS.asItem(), 16000);
			builder.add(ModBlocks.COAL_SLAB.asItem(), 8000);
		});

		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.COAL_STAIRS, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.COAL_SLAB, 5, 5);


	}
}
