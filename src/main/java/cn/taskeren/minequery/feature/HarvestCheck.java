package cn.taskeren.minequery.feature;

import cn.taskeren.minequery.config.MineConfig;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
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
					seed(player.getPos(), pos.down());
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
					seed(player.getPos(), pos.down());
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

	private void seed(Vec3d ppos, BlockPos bpos) {
		if(!MineConfig.boolFeatureReSeeding())
			return;

		new Thread(()->{ // needs break block packet sent first
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			BlockHitResult bhr = new BlockHitResult(ppos, Direction.UP, bpos, false);
			PlayerInteractBlockC2SPacket packet = new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, bhr);
			MinecraftClient.getInstance().player.networkHandler.sendPacket(packet);
		}).start();
	}
}
