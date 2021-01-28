package cn.taskeren.minequery.feature;

import cn.taskeren.minequery.config.MineConfig;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class NotPlacingOnFace implements UseBlockCallback {

	public static boolean notUp, notDown, notNorth, notSouth, notEast, notWest;

	/**
	 * 设置
	 * @param d 方向
	 * @param b 是否禁止
	 */
	public static void set(Direction d, boolean b) {
		if(d == Direction.UP)
			notUp = b;
		if(d == Direction.DOWN)
			notDown = b;
		if(d == Direction.NORTH)
			notNorth = b;
		if(d == Direction.SOUTH)
			notSouth = b;
		if(d == Direction.EAST)
			notEast = b;
		if(d == Direction.WEST)
			notWest = b;
	}

	/**
	 * 重置
	 */
	public static void reset() {
		set(Direction.UP, false);
		set(Direction.DOWN, false);
		set(Direction.NORTH, false);
		set(Direction.SOUTH, false);
		set(Direction.EAST, false);
		set(Direction.WEST, false);
	}

	@Override
	public ActionResult interact(PlayerEntity playerEntity, World world, Hand hand, BlockHitResult blockHitResult) {

		if(!MineConfig.boolFeatureNotPlacingOnFace())
			return ActionResult.PASS;

		final Direction d = blockHitResult.getSide();
		if(notUp || notDown || notNorth || notSouth || notEast || notWest) {
			if(d == Direction.UP && notUp) {
				playerEntity.sendMessage(new TranslatableText("minequery.feature.not-placing-on-face.prevented"), true);
				return ActionResult.FAIL;
			}
			else if(d == Direction.DOWN && notDown) {
				playerEntity.sendMessage(new TranslatableText("minequery.feature.not-placing-on-face.prevented"), true);
				return ActionResult.FAIL;
			}
			else if(d == Direction.NORTH && notNorth) {
				playerEntity.sendMessage(new TranslatableText("minequery.feature.not-placing-on-face.prevented"), true);
				return ActionResult.FAIL;
			}
			else if(d == Direction.SOUTH && notSouth) {
				playerEntity.sendMessage(new TranslatableText("minequery.feature.not-placing-on-face.prevented"), true);
				return ActionResult.FAIL;
			}
			else if(d == Direction.EAST && notEast) {
				playerEntity.sendMessage(new TranslatableText("minequery.feature.not-placing-on-face.prevented"), true);
				return ActionResult.FAIL;
			}
			else if(d == Direction.WEST && notWest) {
				playerEntity.sendMessage(new TranslatableText("minequery.feature.not-placing-on-face.prevented"), true);
				return ActionResult.FAIL;
			}
		}

		return ActionResult.PASS;
	}
}
