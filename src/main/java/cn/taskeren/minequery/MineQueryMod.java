package cn.taskeren.minequery;

import cn.taskeren.minequery.feature.HarvestCheck;
import cn.taskeren.minequery.feature.NotHitIronGolem;
import cn.taskeren.minequery.feature.NotHitVillager;
import cn.taskeren.minequery.feature.NotPlacingOnFace;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;

public class MineQueryMod implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		System.out.println("MineQuery!");
		AttackBlockCallback.EVENT.register(new HarvestCheck());
		AttackEntityCallback.EVENT.register(new NotHitVillager());
		AttackEntityCallback.EVENT.register(new NotHitIronGolem());
		UseBlockCallback.EVENT.register(new NotPlacingOnFace());
	}

}
