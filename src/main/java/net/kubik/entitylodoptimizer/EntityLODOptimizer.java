package net.kubik.entitylodoptimizer;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityLODOptimizer implements ModInitializer {
	public static final String MOD_ID = "entitylodoptimizer";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Entity LOD Mod Initialized");
		ServerTickEvents.START_SERVER_TICK.register(this::onServerTick);
	}

	private void onServerTick(MinecraftServer server) {
		for (ServerWorld world : server.getWorlds()) {
			for (Entity entity : world.iterateEntities()) {
				if (entity instanceof MobEntity mobEntity) {
					adjustEntityAI(mobEntity, world);
				}
			}
		}
	}

	private void adjustEntityAI(Entity entity, ServerWorld world) {
		if (!(entity instanceof LivingEntity livingEntity)) {
			return;
		}

		if (entity instanceof PlayerEntity) {
			return;
		}

		PlayerEntity nearestPlayer = world.getClosestPlayer(entity, -1);
		if (nearestPlayer == null) {
			return;
		}

		double distanceSquared = entity.squaredDistanceTo(nearestPlayer);

		if (distanceSquared < 100) {
			enableFullAI((MobEntity) livingEntity);
		} else if (distanceSquared < 10000) {
			enableSimplifiedAI((MobEntity) livingEntity);
		} else {
			disableAI((MobEntity) livingEntity);
		}
	}

	private void enableFullAI(MobEntity entity) {
		entity.setAiDisabled(false);
	}

	private void enableSimplifiedAI(MobEntity entity) {
		entity.setAiDisabled(false);

		int updateInterval = 40;
		if (entity.age % updateInterval != 0) {
			entity.setSilent(true);
		} else {
			entity.setSilent(false);
			entity.tickMovement();
		}
	}

	private void disableAI(MobEntity entity) {
		entity.setAiDisabled(true);
	}
}