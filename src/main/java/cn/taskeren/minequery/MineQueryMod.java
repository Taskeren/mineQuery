package cn.taskeren.minequery;

import cn.taskeren.minequery.feature.HarvestCheck;
import cn.taskeren.minequery.feature.NotHitVillager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;

public class MineQueryMod implements ModInitializer {
	@Override
	public void onInitialize() {
		System.out.println("MineQuery!");
		AttackBlockCallback.EVENT.register(new HarvestCheck());
		AttackEntityCallback.EVENT.register(new NotHitVillager());
	}
}
