package cn.taskeren.minequery.mixin;

import cn.taskeren.minequery.Features;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DeathScreen.class)
public abstract class AutoRevive {

	@Shadow protected abstract void onConfirmQuit(boolean quit);

	@Inject(at = @At("RETURN"), method = "init()V")
	@SuppressWarnings({"null"}) // MinecraftClient is NOT null
	private void init(CallbackInfo info) {
		if(Features.enableAutoRevive) {
			onConfirmQuit(false); // Respawn
			MinecraftClient.getInstance().player.sendMessage(new TranslatableText("minequery.feature.autorevive"), true);
		}
	}
}
