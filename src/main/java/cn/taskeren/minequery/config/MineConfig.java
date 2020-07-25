package cn.taskeren.minequery.config;

import cn.taskeren.tconfig.Configuration;
import com.mojang.brigadier.builder.ArgumentBuilder;

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

	public static void initializeCommand(ArgumentBuilder b) {
		I.initializeCommand(b);
	}

	static void updateCaches() {
		// TODO: config caches
	}

	public static boolean boolFeatureAutorevive() {
		return ITEM_FEATURE_AUTOREVIVE.getValue(C);
	}

	public static boolean boolFeatureHarvestx() {
		return ITEM_FEATURE_HARVESTX.getValue(C);
	}

	public static boolean boolFeatureHarvestxCrops() {
		return ITEM_FEATURE_HARVESTX_CROPS.getValue(C);
	}

	public static boolean boolFeatureHarvestxNetherWart() {
		return ITEM_FEATURE_HARVESTX_NETHERWART.getValue(C);
	}

	public static boolean boolFeatureHarvestxCactus() {
		return ITEM_FEATURE_HARVESTX_CACTUS.getValue(C);
	}

	public static boolean boolFeatureHarvestxSugarCane() {
		return ITEM_FEATURE_HARVESTX_SUGAR_CANE.getValue(C);
	}

	public static boolean boolFeatureHarvestxStems() {
		return ITEM_FEATURE_HARVESTX_STEMS.getValue(C);
	}

}
