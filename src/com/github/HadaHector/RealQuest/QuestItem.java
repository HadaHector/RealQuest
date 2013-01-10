package com.github.HadaHector.RealQuest;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class QuestItem {
	int id;
	int data;
	String name;
	
	public QuestItem(int i,int d,String n)
	{
		id=i;
		data=d;
		name=n;
	}
	
	public String toString()
	{
		return (name+" ("+id+","+data+")");
	}
	
	public ItemStack makeItemStack()
	{
		ItemStack item = new ItemStack(id,data);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setAmount(1);
		item.setItemMeta(meta);
		return item;
	}
}
