package cn.taskeren.minequery.feature;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class ReSeeding {

	private static final ExecutorService TH_POOL = Executors.newFixedThreadPool(12);

	private ReSeeding() {}

	/**
	 * Schedule a new PlayerInteractBlockC2SPacket sending task
	 * @param p Player who harvests
	 * @param b Block which was harvested(not Farmland)
	 */
	static void schedule(PlayerEntity p, BlockPos b) {
		TH_POOL.submit(()->{
			Vec3d ppos = p.getPos();
			BlockPos bpos = b.down();
			BlockHitResult bhr = new BlockHitResult(ppos, Direction.UP, bpos, false);
			PlayerInteractBlockC2SPacket packet = new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, bhr);
			try {
				TimeUnit.MILLISECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				MinecraftClient.getInstance().player.networkHandler.sendPacket(packet);
			}
		});
	}

}
