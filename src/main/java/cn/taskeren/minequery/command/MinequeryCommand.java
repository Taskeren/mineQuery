package cn.taskeren.minequery.command;

import cn.taskeren.minequery.Features;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import io.github.cottonmc.clientcommands.ArgumentBuilders;
import io.github.cottonmc.clientcommands.ClientCommandPlugin;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import net.minecraft.text.TranslatableText;

@SuppressWarnings({"unused"})
public class MinequeryCommand implements ClientCommandPlugin {

	@Override
	public void registerCommands(CommandDispatcher<CottonClientCommandSource> dispatcher) {
		dispatcher.register(
				ArgumentBuilders.literal("minequery")
					.then(ArgumentBuilders.literal("harvest-check").then(ArgumentBuilders.argument("enable", BoolArgumentType.bool())
						.executes(ctx->{
							boolean enable = BoolArgumentType.getBool(ctx, "enable");
							Features.enableHarvestCheck = enable;
							ctx.getSource().sendFeedback(new TranslatableText(enable?"minequery.feature.harvest-check.enabled":"minequery.feature.harvest-check.disabled"));
							return 1;
						})))
					.then(ArgumentBuilders.literal("autorevive").then(ArgumentBuilders.argument("enable", BoolArgumentType.bool())
						.executes(ctx->{
							boolean enable = BoolArgumentType.getBool(ctx, "enable");
							Features.enableAutoRevive = enable;
							ctx.getSource().sendFeedback(new TranslatableText(enable?"minequery.feature.autorevive.enabled":"minequery.feature.autorevive.disabled"));
							return 1;
						})))
		);
	}
}
