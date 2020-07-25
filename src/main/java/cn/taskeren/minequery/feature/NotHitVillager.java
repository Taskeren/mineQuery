package cn.taskeren.minequery.feature;

import cn.taskeren.minequery.config.MineConfig;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class NotHitVillager implements AttackEntityCallback {

	@Override
	public ActionResult interact(PlayerEntity playerEntity, World world, Hand hand, Entity entity, EntityHitResult entityHitResult) {
		if(MineConfig.boolFeatureNotHitVillager()) {
			if(entity instanceof VillagerEntity) { // WanderingTraderEntity is not a type of VillagerEntity.
				return ActionResult.FAIL;
			}
		}
		return ActionResult.PASS;
	}
}
