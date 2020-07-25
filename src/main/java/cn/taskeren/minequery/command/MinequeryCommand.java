package cn.taskeren.minequery.command;

import cn.taskeren.minequery.config.MineConfig;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.cottonmc.clientcommands.ClientCommandPlugin;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;

import static io.github.cottonmc.clientcommands.ArgumentBuilders.literal;

@SuppressWarnings({"unused"})
public class MinequeryCommand implements ClientCommandPlugin {

	@Override
	public void registerCommands(CommandDispatcher<CottonClientCommandSource> dispatcher) {
		LiteralArgumentBuilder<CottonClientCommandSource> root = literal("minequery").then(commandFeatures());
		dispatcher.register(root);
	}

	private LiteralArgumentBuilder<CottonClientCommandSource> commandFeatures() {
		LiteralArgumentBuilder<CottonClientCommandSource> b = literal("features");
		MineConfig.initializeCommand(b);
		return b;
	}

}
