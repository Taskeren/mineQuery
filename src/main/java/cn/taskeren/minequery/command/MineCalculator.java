package cn.taskeren.minequery.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.github.cottonmc.clientcommands.ArgumentBuilders;
import io.github.cottonmc.clientcommands.ClientCommandPlugin;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import io.github.endreman0.calculator.Calculator;
import io.github.endreman0.calculator.expression.Variable;
import io.github.endreman0.calculator.expression.type.Type;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class MineCalculator implements ClientCommandPlugin {

	private static int run(CommandContext<CottonClientCommandSource> ctx) {
		String equation = StringArgumentType.getString(ctx, "equation");
		Text feedback;
		try {
			Type type = Calculator.calculate(equation);
			feedback = new TranslatableText("minequery.calculator.done", Formatting.GRAY+equation, Formatting.GREEN+String.valueOf(type));
		} catch (Exception ex) {
			feedback = new TranslatableText("minequery.calculator.error", Formatting.RED.toString()+Formatting.ITALIC.toString()+ex.getMessage());
		}
		ctx.getSource().sendFeedback(feedback, false);
		return Command.SINGLE_SUCCESS;
	}

	@Override
	public void registerCommands(CommandDispatcher<CottonClientCommandSource> dispatcher) {
		// init
		Variable.initConstants();

		dispatcher.register(ArgumentBuilders.literal("=").then(ArgumentBuilders.argument("equation", StringArgumentType.greedyString()).executes(MineCalculator::run)));
	}
}
