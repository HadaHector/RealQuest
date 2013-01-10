package com.github.HadaHector.RealQuest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.inventory.ItemStack;


public class RealQuest extends JavaPlugin{
	
	List<QuestItem> itemList = new ArrayList<QuestItem>();
	
	RealQuestEventManager listen = new RealQuestEventManager();
	
    @Override
    public void onEnable(){
    	
    	List<String> list = this.getConfig().getStringList("items");
    	boolean listitems = this.getConfig().getBoolean("listitemsonload");
    	for (int a=0;a<list.size();a++)
    	{
    		int id = this.getConfig().getInt("ids."+list.get(a));
    		if (id==0) continue;
    		int data = this.getConfig().getInt("datas."+list.get(a));
    		String name = this.getConfig().getString("names."+list.get(a));
    		if (name==null || name.isEmpty()) continue;
    		itemList.add(new QuestItem(id,data,name));
    		if (listitems)
    			getLogger().info(a+". "+itemList.get(a).toString());
    	}
    	this.getServer().getPluginManager().registerEvents(listen, this);
    	
    	getLogger().info("Loaded and enabled succesfully!");
    }
 


	@Override
    public void onDisable() {
    	getLogger().info("Disabled.");
    }
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("addQI")){
			Player player;
			if (sender instanceof Player) player = (Player) sender; else return true;
			if (args.length==1 )
			{
				String key = args[0];
				for (int a=0;a<itemList.size();a++)
				{
					if (itemList.get(a).name.equals(key))
					{
						ItemStack i;
						i = itemList.get(a).makeItemStack();
						if (i==null) player.sendMessage("null :(");
						player.getInventory().addItem(i);
					player.sendMessage("Giving some questitem: "+i.toString());
					}
				}
			}
			return true;
		} //If this has happened the function will return true. 
	        // If this hasn't happened the a value of false will be returned.
		return false; 
	}
	

}
