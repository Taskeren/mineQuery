package cn.taskeren.minequery.feature;

import cn.taskeren.minequery.config.MineConfig;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class HarvestCheck implements AttackBlockCallback {

	@Override
	public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockPos pos, Direction direction) {
		if(!MineConfig.boolFeatureHarvestx())
			return ActionResult.PASS;

		BlockState state = world.getBlockState(pos);

		if(MineConfig.boolFeatureHarvestxCrops()) {
			if(state.getBlock() instanceof CropBlock) {
				CropBlock crop = (CropBlock) state.getBlock();
				int age = state.get(crop.getAgeProperty());
				int max = crop.getMaxAge();
				if(age != max) {
					return ActionResult.FAIL;
				}
				else {
					ReSeeding.schedule(player, pos);
				}
			}
		}

		if(MineConfig.boolFeatureHarvestxNetherWart()) {
			if(state.getBlock() instanceof NetherWartBlock) { // fix #1
				int age = state.get(NetherWartBlock.AGE);
				if(age != 3) {
					return ActionResult.FAIL;
				}
				else {
					ReSeeding.schedule(player, pos);
				}
			}
		}

		if(MineConfig.boolFeatureHarvestxCactus()) {
			if(state.getBlock() == Blocks.CACTUS) {
				if(world.getBlockState(pos.down()).getBlock() != Blocks.CACTUS) { // Has No Cactus below
					return ActionResult.FAIL;
				}
			}
		}

		if(MineConfig.boolFeatureHarvestxSugarCane()) {
			if(state.getBlock() == Blocks.SUGAR_CANE) {
				if(world.getBlockState(pos.down()).getBlock() != Blocks.SUGAR_CANE) { // Has No Sugar Cane below
					return ActionResult.FAIL;
				}
			}
		}

		if(MineConfig.boolFeatureHarvestxStems()) {
			if(state.getBlock() instanceof StemBlock) {
				return ActionResult.FAIL;
			}
		}

		return ActionResult.PASS;
	}

}
