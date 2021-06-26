package com.coxspecialloothider;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.MessageNode;
import net.runelite.client.chat.ChatMessageManager;
import java.util.ArrayList;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.WidgetID;

@Slf4j
@PluginDescriptor(
	name = "CoX Censor"
)
public class CoxSpecialLootHiderPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private CoxSpecialLootHiderConfig config;

	@Inject
	private ChatMessageManager chatMessageManager;

	private ArrayList<ChatMessage> turnOffMessages = new ArrayList<ChatMessage>();
	private ArrayList<String> turnOffStrings= new ArrayList<String>();

	private static final String[] listOfItems = {"Dexterous prayer scroll", "Arcane prayer scroll", "Twisted buckler",
			"Dragon hunter crossbow", "Dinh's bulwark", "Ancestral hat", "Ancestral robe top", "Ancestral robe bottom",
			"Dragon claws", "Elder maul", "Kodai insignia", "Twisted bow"};

	@Override
	protected void startUp() throws Exception
	{
		turnOffMessages.clear();
		client.refreshChat();
	}

	@Override
	protected void shutDown() throws Exception
	{
		showLoot();
	}

	//Shows loot
	private void showLoot(){
		//Shows the player the messages that were censored
		//Multiple items are stored in the ArrayList and re-sent upon turning off the plugin
		//On chest open the list is cleared
		if(!turnOffMessages.isEmpty()){
			for(int i = 0; i < turnOffMessages.size(); i++){
				//Gets the original message as string, sets the string at the
				//referenced ChatMessage as that string
				turnOffMessages.get(i).setMessage(turnOffStrings.get(i));
				//Updates the front end of that node
				final MessageNode messageNode = turnOffMessages.get(i).getMessageNode();
				messageNode.setRuneLiteFormatMessage(turnOffStrings.get(i));
				chatMessageManager.update(messageNode);
			}
			turnOffMessages.clear();
			turnOffStrings.clear();
		}
		client.refreshChat();
	}

	//Shows loot upon looting the chest
	@Subscribe
	public void onWidgetLoaded(WidgetLoaded wid){
		if(wid.getGroupId() == WidgetID.CHAMBERS_OF_XERIC_REWARD_GROUP_ID){
			showLoot();
		}
	}

	@Subscribe
	public void onChatMessage(ChatMessage chatMessage)
	{
		if (chatMessage.getType() == ChatMessageType.FRIENDSCHATNOTIFICATION)
		{
			//Shown when completing a raid.
			//Will turn off before a purple is shown, so if someone leaves a raid and doesn't open chest
			//but gets a purple later, this will clear it so there is no funny business.
			if(chatMessage.getMessage().contains("Congratulations - your raid is complete!")){
				turnOffMessages.clear();
				turnOffStrings.clear();
			}

			//Iterating through the list of CoX uniques
			for (String item : listOfItems){

				//Check if item is in the message
				if(chatMessage.getMessage().contains(item)){

					//Adds it to the list of messages for the when plugin is turned off
					turnOffMessages.add(chatMessage);
					turnOffStrings.add(chatMessage.getMessage());
					
					//Replaces item name with ???
					String msg = chatMessage.getMessage().replace(item, "???");

					//Changing the message of the chatMessage. This only sets the message on the backend
					chatMessage.setMessage(msg);

					//Updating it on the UI end
					final MessageNode messageNode = chatMessage.getMessageNode();
					messageNode.setRuneLiteFormatMessage(msg);
					chatMessageManager.update(messageNode);
					client.refreshChat();
					break;
				}
			}
		}
	}

	@Provides
	CoxSpecialLootHiderConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(CoxSpecialLootHiderConfig.class);
	}
}
