package cn.taskeren.minequery;

import cn.taskeren.minequery.feature.HarvestCheck;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;

public class MineQueryMod implements ModInitializer {
	@Override
	public void onInitialize() {
		System.out.println("Hello Fabric world!");

		AttackBlockCallback.EVENT.register(new HarvestCheck());
	}
}
