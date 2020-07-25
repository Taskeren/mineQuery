package cn.taskeren.minequery.config;

import cn.taskeren.tconfig.Configuration;
import cn.taskeren.tconfig.Property;
import com.google.common.collect.Lists;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import net.minecraft.text.TranslatableText;

import java.util.LinkedList;

import static io.github.cottonmc.clientcommands.ArgumentBuilders.argument;
import static io.github.cottonmc.clientcommands.ArgumentBuilders.literal;

abstract class I<V> {

	private static final LinkedList<I> ITEMS = Lists.newLinkedList();

	private final String name;
	private final String description;

	private String category;
	private String defaultValue;

	I(String name, String description, String category, String defaultValue) {
		this.name = name;
		this.description = description;
		this.category = category;
		this.defaultValue = defaultValue;

		ITEMS.add(this);
	}

	@SuppressWarnings({"SameParameterValue"})
	private static I<Boolean> ofBool(String name, String description, String category, boolean defaultValue) {
		return new I<Boolean>(
				name,
				description,
				category,
				Boolean.toString(defaultValue)
		) {
			@Override
			public Boolean getValue(Configuration config) {
				return getProperty(config).getBoolean();
			}

			@Override
			public void setValue(Configuration config, Boolean bool) {
				getProperty(config).set(bool);
				config.save();
			}
		};
	}

	Property getProperty(Configuration config) {
		return config.get(category, name, defaultValue, description);
	}

	public abstract V getValue(Configuration config);
	public abstract void setValue(Configuration config, V value);

	/* General Operations */
	static void initializeConfiguration() {
		ITEMS.forEach(item -> item.getValue(MineConfig.C));
	}

	static void initializeCommand(LiteralArgumentBuilder<CottonClientCommandSource> b) {
		ITEMS.forEach(item -> {
			b.then(literal(item.name).then(argument("enable", BoolArgumentType.bool()).executes(ctx -> {
				boolean val = BoolArgumentType.getBool(ctx, "enable");
				item.setValue(MineConfig.C, val);
				MineConfig.updateCaches();
				ctx.getSource().sendFeedback(new TranslatableText("minequery.feature."+item.name+"."+(val?"enabled":"disabled")), false);
				return Command.SINGLE_SUCCESS;
			})));
		});
	}

	/* Frenquently Used Functions */
	private Property fufGetProp(Configuration c) {
		return c.get(category, name, description, defaultValue);
	}

	/* Configuration Items */
	static final I<Boolean> ITEM_FEATURE_AUTOREVIVE = ofBool(
			"autorevive",
			"Autorevive: auto respawn",
			"feature",
			true
	);

	static final I<Boolean> ITEM_FEATURE_HARVESTX = ofBool(
			"harvestx",
			"Harvestx: prevent breaking immature crops.",
			"feature",
			true
	);

	static final I<Boolean> ITEM_FEATURE_HARVESTX_CROPS = ofBool(
			"harvestx.crops",
			"Harvestx#Crops: prevent breaking immature crops like Wheat, Potato, Carrots, etc.",
			"harvestx",
			true
	);

	static final I<Boolean> ITEM_FEATURE_HARVESTX_NETHERWART = ofBool(
			"harvestx.netherwart",
			"Harvestx#NetherWart: prevent breaking immature Nether Wart.",
			"harvestx",
			true
	);

	static final I<Boolean> ITEM_FEATURE_HARVESTX_CACTUS = ofBool(
			"harvestx.cactus",
			"Harvestx#Cactus: prevent breaking the bottom Cactus.",
			"harvestx",
			true
	);

	static final I<Boolean> ITEM_FEATURE_HARVESTX_SUGAR_CANE = ofBool(
			"harvestx.sugarcane",
			"Harvestx#SugarCane: prevent breaking the bottom Sugar Cane.",
			"harvestx",
			true
	);

	static final I<Boolean> ITEM_FEATURE_HARVESTX_STEMS = ofBool(
			"harvestx.stems",
			"Harvestx#Cactus: prevent breaking the stems of Pumpkin and Melon.",
			"harvestx",
			true
	);

}
