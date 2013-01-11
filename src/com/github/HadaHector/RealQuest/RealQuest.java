package com.github.HadaHector.RealQuest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class RealQuest extends JavaPlugin{
	
	List<QuestItem> itemList = new ArrayList<QuestItem>();
	Map<Player,RealQuestPlayer> playermap = new HashMap<Player,RealQuestPlayer>();
	
	RealQuestEventManager listen = new RealQuestEventManager(this);
	
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
    	
    	//preparing online players (if its a reload)
    	Player[] players = this.getServer().getOnlinePlayers();
    	for (int a=0; a<players.length;a++)
    	{
    		prepareplayer(players[a]);
    	}
    	
    	getLogger().info("Loaded and enabled succesfully!");
    }
   


	@Override
    public void onDisable() {
    	getLogger().info("Disabled.");
    }
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("addqi")){
			Player player;
			if (sender instanceof Player) player = (Player) sender; else return true;
			if (args.length==1 ||  args.length==2)
			{
				String key = args[0];
				for (int a=0;a<itemList.size();a++)
				{
					if (itemList.get(a).name.equalsIgnoreCase(key))
					{
						ItemStack i;
						i = itemList.get(a).makeItemStack();
						int am = 1;
						if (args.length==2) am= Integer.parseInt(args[1]);
						if (am>0) i.setAmount(am);
						if (i==null) player.sendMessage("null :(");
						player.getInventory().addItem(i);
					player.sendMessage("Giving some questitem: "+itemList.get(a).name);
					}
				}
			}
			return true;
		} //If this has happened the function will return true. 
	        // If this hasn't happened the a value of false will be returned.
		return false; 
	}
	
	public boolean isQuestItem(ItemStack item)
	{
		if (item==null) return false;
		ItemMeta meta = item.getItemMeta();
		if (meta==null) return false;
		String name = meta.getDisplayName();
		if (name==null) return false;
		for (int a=0;a<itemList.size();a++)
		{
			if (itemList.get(a).name.equals(name)
					&& itemList.get(a).id==item.getTypeId()
					&& !meta.toString().contains("repair-cost")) return true;//TODO optimize
		}
		return false;
	}
	
	public void prepareplayer(Player p)
	{
	playermap.put(p,new RealQuestPlayer(getServer().createInventory(null, 3*9,"QuestBag")));
	p.sendMessage("Üdv az RPG szerveren");
	}
	

}
