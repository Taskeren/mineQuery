package cn.taskeren.minequery.feature;

import cn.taskeren.minequery.config.MineConfig;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class NotHitIronGolem implements AttackEntityCallback {

	@Override
	public ActionResult interact(PlayerEntity playerEntity, World world, Hand hand, Entity entity, EntityHitResult entityHitResult) {
		if(entity instanceof IronGolemEntity) {
			int level = MineConfig.intFeatureIronGolem();
			boolean isPlayerCreated = ((IronGolemEntity) entity).isPlayerCreated();
			switch (level) {
				case 0: // Hit All
					return ActionResult.PASS;
				case 1: // Hit Village Spawn Only
					return isPlayerCreated ? ActionResult.FAIL : ActionResult.PASS;
				case 2: // Hit Player Created Only
					return isPlayerCreated ? ActionResult.PASS : ActionResult.FAIL;
				case 3: // Hit None
					return ActionResult.FAIL;
			}
		}
		return ActionResult.PASS;
	}
}
