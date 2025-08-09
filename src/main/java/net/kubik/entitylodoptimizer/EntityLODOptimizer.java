package net.kubik.entitylodoptimizer;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityLODOptimizer implements ModInitializer {
	public static final String MOD_ID = "entitylodoptimizer";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final int SCAN_INTERVAL_TICKS = 20;
	private static final int SIMPLIFIED_UPDATE_INTERVAL = 40;
	private static final double FULL_RADIUS = 10.0D;
	private static final double SIMPLIFIED_RADIUS = 100.0D;

	private static final double FULL_RADIUS_SQ = FULL_RADIUS * FULL_RADIUS;
	private static final double SIMPLIFIED_RADIUS_SQ = SIMPLIFIED_RADIUS * SIMPLIFIED_RADIUS;

	private long tickCounter = 0;

	@Override
	public void onInitialize() {
		LOGGER.info("Entity LOD Optimizer initializing (scanInterval={} ticks, simplifiedInterval={} ticks)", SCAN_INTERVAL_TICKS, SIMPLIFIED_UPDATE_INTERVAL);
		ServerTickEvents.START_SERVER_TICK.register(this::onServerTick);
	}

	private void onServerTick(MinecraftServer server) {
		tickCounter++;
		if (tickCounter % SCAN_INTERVAL_TICKS != 0) return;

		for (ServerWorld world : server.getWorlds()) {
			for (Entity entity : world.iterateEntities()) {
				if (entity instanceof MobEntity mob && !(entity instanceof PlayerEntity)) {
					adjustEntityAI(mob, world);
				}
			}
		}
	}

	private void adjustEntityAI(MobEntity mob, ServerWorld world) {
		PlayerEntity nearestPlayer = world.getClosestPlayer(mob, SIMPLIFIED_RADIUS);
		double distSq = nearestPlayer == null ? Double.POSITIVE_INFINITY : mob.squaredDistanceTo(nearestPlayer);

		if (distSq <= FULL_RADIUS_SQ) {
			enableFullAI(mob);
		} else if (distSq <= SIMPLIFIED_RADIUS_SQ) {
			enableSimplifiedAI(mob, world);
		} else {
			disableAI(mob);
		}
	}

	private void enableFullAI(MobEntity mob) {
		mob.setAiDisabled(false);
		mob.setSilent(false);
	}

	private void enableSimplifiedAI(MobEntity mob, ServerWorld world) {
		mob.setAiDisabled(false);
		if (world.getTime() % SIMPLIFIED_UPDATE_INTERVAL == 0) {
			mob.tickMovement();
			mob.setSilent(false);
		} else {
			mob.setSilent(true);
		}
	}

	private void disableAI(MobEntity mob) {
		mob.setAiDisabled(true);
		mob.setSilent(true);
	}
}
