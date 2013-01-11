package com.github.HadaHector.RealQuest;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class RealQuestPlayer {

	
	Inventory questbag;
	boolean questBagOpened=false;
	
	public RealQuestPlayer(Inventory inv){
		questbag = inv;
	};
	
	public void showQuestBag(Player player)
	{
		player.openInventory(questbag);
		questBagOpened = true;
	}
	
	
}
