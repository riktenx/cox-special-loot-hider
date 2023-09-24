package com.coxspecialloothider;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("censorConfig")
public interface CoxSpecialLootHiderConfig extends Config
{
    @ConfigItem(
            keyName = "soloOnly",
            name = "Only censor your purples?",
            description = "Only censor if the purple is yours",
            position = 1
    )
    default boolean soloOnly()
    {
        return false;
    }
}
