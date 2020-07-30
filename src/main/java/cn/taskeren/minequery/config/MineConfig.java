package cn.taskeren.minequery.config;

import cn.taskeren.tconfig.Configuration;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;

import java.io.File;

import static cn.taskeren.minequery.config.I.*;

public class MineConfig {

	static final Configuration C;

	static {
		C = new Configuration(
				new File("./config/minequery.config")
		);

		// Load defaults and save
		I.initializeConfiguration();
		C.save();
	}

	public static void initializeCommand(LiteralArgumentBuilder<CottonClientCommandSource> b) {
		I.initializeCommand(b);
	}

	static void updateCaches() {
		// TODO: config caches
	}

	public static boolean boolFeatureAutorevive() {
		return ITEM_FEATURE_AUTOREVIVE.getValue();
	}

	public static boolean boolFeatureHarvestx() {
		return ITEM_FEATURE_HARVESTX.getValue();
	}

	public static boolean boolFeatureHarvestxCrops() {
		return ITEM_FEATURE_HARVESTX_CROPS.getValue();
	}

	public static boolean boolFeatureHarvestxNetherWart() {
		return ITEM_FEATURE_HARVESTX_NETHERWART.getValue();
	}

	public static boolean boolFeatureHarvestxCactus() {
		return ITEM_FEATURE_HARVESTX_CACTUS.getValue();
	}

	public static boolean boolFeatureHarvestxSugarCane() {
		return ITEM_FEATURE_HARVESTX_SUGAR_CANE.getValue();
	}

	public static boolean boolFeatureHarvestxStems() {
		return ITEM_FEATURE_HARVESTX_STEMS.getValue();
	}

	public static boolean boolFeatureNotHitVillager() {
		return ITEM_FEATURE_NOT_HIT_VILLAGERS.getValue();
	}

	public static int intFeatureIronGolem() {
		return ITEM_FEATURE_NOT_HIT_IRON_GOLEM.getValue();
	}

	public static boolean boolFeatureReSeeding() {
		return ITEM_FEATURE_RESEEDING.getValue();
	}

}
