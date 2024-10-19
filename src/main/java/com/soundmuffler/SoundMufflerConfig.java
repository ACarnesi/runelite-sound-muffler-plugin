package com.soundmuffler;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("soundmuffler")
public interface SoundMufflerConfig extends Config
{
	@ConfigSection(
			name = "Activities",
			description = "Activities to Mute",
			position = 0
	)
	String activitiesSection = "Activities";

	@ConfigItem(
			keyName = "muteWoodcutting",
			name = "Woodcutting",
			description = "Mutes other player's woodcutting sounds",
			section = activitiesSection
	)
	default boolean muteWoodcutting() { return false; }

	@ConfigItem(
			keyName = "muteMining",
			name = "Mining",
			description = "Mutes other player's mining sounds",
			section = activitiesSection
	)
	default boolean muteMining()
	{
		return false;
	}

	@ConfigItem(
			keyName = "muteFishing",
			name = "Fishing",
			description = "Mutes other player's fishing sounds",
			section = activitiesSection
	)
	default boolean muteFishing()
	{
		return false;
	}
}
