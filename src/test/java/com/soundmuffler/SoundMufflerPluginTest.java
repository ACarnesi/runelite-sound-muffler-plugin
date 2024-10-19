package com.soundmuffler;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class SoundMufflerPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(SoundMufflerPlugin.class);
		RuneLite.main(args);
	}
}