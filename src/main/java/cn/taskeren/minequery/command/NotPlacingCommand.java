package cn.taskeren.minequery.command;

import cn.taskeren.minequery.feature.NotPlaceOnFace;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.github.cottonmc.clientcommands.ClientCommandPlugin;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import net.minecraft.text.TranslatableText;

import static io.github.cottonmc.clientcommands.ArgumentBuilders.argument;
import static io.github.cottonmc.clientcommands.ArgumentBuilders.literal;

public class NotPlacingCommand implements ClientCommandPlugin {

	@Override
	public void registerCommands(CommandDispatcher<CottonClientCommandSource> commandDispatcher) {
		LiteralArgumentBuilder<CottonClientCommandSource> root = literal("notplace")
				.then(argument("direction", StringArgumentType.word()).then(argument("enable", BoolArgumentType.bool()).executes(NotPlacingCommand::run)));
		commandDispatcher.register(root);
	}

	private static int run(CommandContext<CottonClientCommandSource> ctx) {
		final String d = StringArgumentType.getString(ctx, "direction");
		final Boolean b = BoolArgumentType.getBool(ctx, "enable");

		if(d.equalsIgnoreCase("u") || d.equalsIgnoreCase("up")) {
			NotPlaceOnFace.notUp = b;
			ctx.getSource().sendFeedback(new TranslatableText("minequery.feature.not-place-on-face.set."+b, d), false);
		}
		else if(d.equalsIgnoreCase("d") || d.equalsIgnoreCase("down")) {
			NotPlaceOnFace.notDown = b;
			ctx.getSource().sendFeedback(new TranslatableText("minequery.feature.not-place-on-face.set."+b, d), false);
		}
		else if(d.equalsIgnoreCase("n") || d.equalsIgnoreCase("north")) {
			NotPlaceOnFace.notNorth = b;
			ctx.getSource().sendFeedback(new TranslatableText("minequery.feature.not-place-on-face.set."+b, d), false);
		}
		else if(d.equalsIgnoreCase("s") || d.equalsIgnoreCase("south")) {
			NotPlaceOnFace.notSouth = b;
			ctx.getSource().sendFeedback(new TranslatableText("minequery.feature.not-place-on-face.set."+b, d), false);
		}
		else if(d.equalsIgnoreCase("e") || d.equalsIgnoreCase("east")) {
			NotPlaceOnFace.notEast = b;
			ctx.getSource().sendFeedback(new TranslatableText("minequery.feature.not-place-on-face.set."+b, d), false);
		}
		else if(d.equalsIgnoreCase("w") || d.equalsIgnoreCase("west")) {
			NotPlaceOnFace.notWest = b;
			ctx.getSource().sendFeedback(new TranslatableText("minequery.feature.not-place-on-face.set."+b, d), false);
		}
		else {
			ctx.getSource().sendFeedback(new TranslatableText("minequery.not-place-on-face.direction-not-found"), false);
		}

		return Command.SINGLE_SUCCESS;
	}
}
