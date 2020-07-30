package cn.taskeren.minequery.config;

import cn.taskeren.tconfig.Configuration;
import cn.taskeren.tconfig.Property;
import com.google.common.collect.Lists;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.MathHelper;

import java.util.LinkedList;

import static cn.taskeren.minequery.config.MineConfig.C;
import static io.github.cottonmc.clientcommands.ArgumentBuilders.argument;
import static io.github.cottonmc.clientcommands.ArgumentBuilders.literal;

abstract class I<V> {

	private static final LinkedList<I<Integer>> ITEMS_INT = Lists.newLinkedList();
	private static final LinkedList<I<Boolean>> ITEMS_BOOL = Lists.newLinkedList();

	private final String name;
	private final String description;

	private String category;
	private String defaultValue;

	private int max, min;

	// String Constructor
	I(String name, String description, String category, String defaultValue) {
		this.name = name;
		this.description = description;
		this.category = category;
		this.defaultValue = defaultValue;
	}

	// Integer Constructor
	I(String name, String description, String category, int defaultValue, int max, int min) {
		this.name = name;
		this.description = description;
		this.category = category;
		this.defaultValue = Integer.toString(defaultValue);
		this.max = max;
		this.min = min;
	}

	@SuppressWarnings({"SameParameterValue"})
	private static I<Boolean> ofBool(String name, String description, String category, boolean defaultValue) {
		I<Boolean> i = new I<Boolean>(
				name,
				description,
				category,
				Boolean.toString(defaultValue)
		) {
			@Override
			public Boolean getValue() {
				return getProperty().getBoolean();
			}

			@Override
			public void setValue(Boolean bool) {
				getProperty().set(bool);
				C.save();
			}
		};

		ITEMS_BOOL.add(i);
		return i;
	}

	private static I<Integer> ofInt(String name, String desc, String category, int defaultValue, int max, int min) {
		I<Integer> i = new I<Integer>(
				name,
				desc,
				category,
				defaultValue,
				max,
				min
		) {
			@Override
			Property getProperty() {
				Property prop = super.getProperty();
				prop.setComment(desc + " [range: " + min + " ~ " + max + ", default: " + defaultValue + "]");
				prop.setMinValue(min);
				prop.setMaxValue(max);
				return prop;
			}

			@Override
			public Integer getValue() {
				return getProperty().getInt();
			}

			@Override
			public void setValue(Integer value) {
				getProperty().set(MathHelper.clamp(value, min, max));
				C.save();
			}
		};

		ITEMS_INT.add(i);
		return i;
	}

	Property getProperty() {
		return C.get(category, name, defaultValue, description);
	}

	public abstract V getValue();
	public abstract void setValue(V value);

	/* General Operations */
	static void initializeConfiguration() {
		ITEMS_BOOL.forEach(I::getValue);
	}

	static void initializeCommand(LiteralArgumentBuilder<CottonClientCommandSource> b) {
		ITEMS_BOOL.forEach(item -> {
			b.then(literal(item.name).then(argument("enable", BoolArgumentType.bool()).executes(ctx -> {
				boolean val = BoolArgumentType.getBool(ctx, "enable");
				item.setValue(val);
				MineConfig.updateCaches();
				ctx.getSource().sendFeedback(new TranslatableText("minequery.feature."+item.name+"."+(val?"enabled":"disabled")), false);
				return Command.SINGLE_SUCCESS;
			})));
		});

		// NotHitIronGolem
		b.then(literal("not-hit-iron-golem").then(argument("level", IntegerArgumentType.integer(0, 3)).executes(ctx -> {
			int level = IntegerArgumentType.getInteger(ctx, "level");
			ITEM_FEATURE_NOT_HIT_IRON_GOLEM.setValue(level);
			ctx.getSource().sendFeedback(new TranslatableText("minequery.feature.not-hit-iron-golem."+level), false);
			return Command.SINGLE_SUCCESS;
		})));
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

	static final I<Boolean> ITEM_FEATURE_NOT_HIT_VILLAGERS = ofBool(
			"not-hit-villagers",
			"NotHitVillagers: prevent hitting villagers that cause price rising.",
			"feature",
			true
	);

	static final I<Integer> ITEM_FEATURE_NOT_HIT_IRON_GOLEM = ofInt(
			"not-hit-iron-golem",
			"NotHitIronGolem: 0(hit all), 1(not hit player-created), 2(not hit non-player-created), 3(not hit both)",
			"feature",
			3,
			3,
			0
	);

	static final I<Boolean> ITEM_FEATURE_RESEEDING = ofBool(
			"reseeding",
			"ReSeeding: seed when crops and nether wart is broken",
			"feature",
			true
	);

}
