package com.github.HadaHector.RealQuest;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class RealQuestEventManager implements Listener{
	

	
	@EventHandler(ignoreCancelled = true)
	public void onAnvil(CraftItemEvent event)
	{
		HumanEntity clicker = event.getWhoClicked();
		Player player;
		if (clicker instanceof Player) player = (Player) clicker; else return;
		player.sendMessage("hoppa");
	}

	

}
