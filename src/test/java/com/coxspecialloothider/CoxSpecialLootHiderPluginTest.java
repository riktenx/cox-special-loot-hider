package com.coxspecialloothider;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class CoxSpecialLootHiderPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(CoxSpecialLootHiderPlugin.class);
		RuneLite.main(args);
	}
}