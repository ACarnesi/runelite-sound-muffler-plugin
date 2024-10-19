package com.soundmuffler;

import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.AreaSoundEffectPlayed;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.util.HashSet;

@Slf4j
@PluginDescriptor(
	name = "Sound Muffler",
	description = "Mute other player's activity sounds.",
	tags = {"sound", "volume", "mute", "destence" }
)
public class SoundMufflerPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private SoundMufflerConfig config;

	@VisibleForTesting
	public HashSet<Integer> mutedSoundEffects = new HashSet<>();

	@Override
	public void startUp()
	{
		initializeMutedActivities();
	}

	@Override
	public void shutDown()
	{
		mutedSoundEffects.clear();
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged configChanged)
	{
		if (configChanged.getGroup().equals("soundmuffler"))
		{
			mutedSoundEffects.clear();
			initializeMutedActivities();
		}
	}

	private void initializeMutedActivities() {
		if (config.muteWoodcutting())
		{
			mutedSoundEffects.add(SoundEffectID.WOODCUTTING_CHOP);
			mutedSoundEffects.add(SoundEffectID.CHOP_CHOP);
		}

		if (config.muteMining())
		{
			mutedSoundEffects.add(SoundEffectID.MINING_PICK_SWING_1);
			mutedSoundEffects.add(SoundEffectID.MINING_PICK_SWING_2);
		}

		if (config.muteFishing())
		{
			mutedSoundEffects.add(SoundEffectID.FISHING_SOUND);
			mutedSoundEffects.add(SoundEffectID.FISHING_NET_SOUND);
			mutedSoundEffects.add(SoundEffectID.FISHING_HARPOON_SOUND);
			mutedSoundEffects.add(SoundEffectID.FISHING_BARBARIAN_HARPOON_SOUND);
		}
	}

	@Subscribe
	public void onAreaSoundEffectPlayed(AreaSoundEffectPlayed areaSoundEffectPlayed)
	{
		Actor source = areaSoundEffectPlayed.getSource();
		int soundId = areaSoundEffectPlayed.getSoundId();

		if (source != client.getLocalPlayer() && source instanceof Player)
		{
			if (shouldMute(soundId))
			{
				areaSoundEffectPlayed.consume();
			}
		}
	}

	@Provides
	SoundMufflerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(SoundMufflerConfig.class);
	}

	private boolean shouldMute(int soundId) {
		return mutedSoundEffects.contains(soundId);
	}
}
