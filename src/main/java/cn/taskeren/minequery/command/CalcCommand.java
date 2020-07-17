package cn.taskeren.minequery.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import io.github.cottonmc.clientcommands.ArgumentBuilders;
import io.github.cottonmc.clientcommands.ClientCommandPlugin;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import net.minecraft.text.TranslatableText;

/**
 * 计算指令
 *  - toNether <x> <z> 计算主世界对应的地狱坐标
 *  - toOverworld <x> <z> 计算地狱对应主世界的坐标
 */
public class CalcCommand implements ClientCommandPlugin {

	@Override
	public void registerCommands(CommandDispatcher<CottonClientCommandSource> dispatcher) {

		dispatcher.register(
				ArgumentBuilders.literal("calc")
						.then(ArgumentBuilders.literal("toNether")
							.then(ArgumentBuilders.argument("x", IntegerArgumentType.integer())
								.then(ArgumentBuilders.argument("z", IntegerArgumentType.integer())
									.executes(ctx->{
										int x = IntegerArgumentType.getInteger(ctx, "x");
										int z = IntegerArgumentType.getInteger(ctx, "z");
										ctx.getSource().sendFeedback(new TranslatableText("minequery.calc.ow2nw", Math.floor((float)x/8), Math.floor((float)z/8)));
										return 1;
									})
								)))
						.then(ArgumentBuilders.literal("toOverworld")
							.then(ArgumentBuilders.argument("x", IntegerArgumentType.integer())
								.then(ArgumentBuilders.argument("z", IntegerArgumentType.integer())
									.executes(ctx->{
										int x = IntegerArgumentType.getInteger(ctx, "x");
										int z = IntegerArgumentType.getInteger(ctx, "z");
										ctx.getSource().sendFeedback(new TranslatableText("minequery.calc.nw2ow", Math.floor(x*8), Math.floor(z*8)));
										return 1;
									})
								)))
		);

	}
}
