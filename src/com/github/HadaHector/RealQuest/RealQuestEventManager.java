package com.github.HadaHector.RealQuest;

import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
//import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class RealQuestEventManager implements Listener{
	
	RealQuest plugin;
	
	
	RealQuestEventManager(RealQuest p)
	{
		plugin = p;
	}
/*

	@EventHandler(ignoreCancelled = true)
	public void onOpen(InventoryOpenEvent event)
	{
		HumanEntity clicker = event.getPlayer();
		Player player;
		if (clicker instanceof Player) player = (Player) clicker; else return;
		player.sendMessage("kinyit");
	}
*/
	
	@EventHandler(ignoreCancelled = true)
	public void onClose(InventoryCloseEvent event)
	{
		HumanEntity clicker = event.getPlayer();
		Player player;
		if (clicker instanceof Player) player = (Player) clicker; else return;
		RealQuestPlayer ppp = plugin.playermap.get(player);
		if (ppp!=null && ppp.questBagOpened)
		{
		ppp.questBagOpened=false;
		ItemStack[] cuccok = ppp.questbag.getContents();
		for (int a=0;a<cuccok.length;a++)
		{
			if (cuccok[a]!=null)
			if (! plugin.isQuestItem(cuccok[a]))
				cuccok[a].setAmount(0);
		}
		ppp.questbag.setContents(cuccok);
		player.sendMessage("QuestBag close");
		}
		
	}
	
	
	@EventHandler(ignoreCancelled = true)
	public void onJoin(PlayerJoinEvent event)
	{
		plugin.prepareplayer(event.getPlayer());
	}

	@EventHandler(ignoreCancelled = true)
	public void onDC(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();	
		plugin.playermap.remove(player);
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onUse(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();	
		if (event.getAction()==Action.RIGHT_CLICK_BLOCK)
		if (player.getItemInHand().getTypeId()==380)
		if (player.getItemInHand().getItemMeta().getDisplayName().equals("QuestBag"))
		{
			plugin.playermap.get(player).showQuestBag(player);
			event.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onPickUp(PlayerPickupItemEvent event)
	{
		Player player = event.getPlayer();	
		if (plugin.isQuestItem(event.getItem().getItemStack()))
			{	
			// Player player = event.getPlayer();	
			plugin.playermap.get(player).questbag.addItem(event.getItem().getItemStack());
			event.getItem().remove();
			player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1, 0.5F);
			player.sendMessage("+"+event.getItem().getItemStack().getAmount()+" "+event.getItem().getItemStack().getItemMeta().getDisplayName());
			event.setCancelled(true);
			}
	}
	

	

}
