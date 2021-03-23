package games.skweekychair.simplehomes;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.ServicePriority;

public class SimpleHomesPlugin extends JavaPlugin {
   
    SimpleHomesAPI apiImpl = new SimpleHomesAPIImpl(this, getConfig().getConfigurationSection("user"));

    // Fired when plugin is first enabled
    @Override
    public void onEnable() {
        
        saveDefaultConfig();

        ConfigurationSection users = getConfig().getConfigurationSection("user");

        this.getCommand("addhome").setExecutor(new CommandAddHome(this, users));
        this.getCommand("home").setExecutor(new CommandHome(users));
        this.getCommand("delhome").setExecutor(new CommandDelHome(this, users));

        this.getServer().getServicesManager().register(SimpleHomesAPI.class, apiImpl, this, ServicePriority.Normal);

    }

    // Fired when plugin is disabled
    @Override
    public void onDisable() {
        saveConfig();
        this.getServer().getServicesManager().unregister(SimpleHomesAPI.class, apiImpl);
    }

}

/* 
    CODE GRAVEYARD

ConfigurationSection player1Section = getConfig().createSection("player1");
player1Section.set("home1", new Location(Bukkit.getWorld("world"), 2, 2, 2));
player1Section.set("home2", new Location(Bukkit.getWorld("world"), 3, 3, 3));
ConfigurationSection player2Section = getConfig().createSection("player2");
player2Section.set("home3", new Location(Bukkit.getWorld("world"), 4, 4, 4));
player2Section.set("home4", new Location(Bukkit.getWorld("world"), 5, 5, 5));
saveConfig(); */


/*HashMap<String, Location> player1 = new HashMap<String, Location>();
player1.put("home1", new Location(Bukkit.getWorld("world"), 2, 2, 2));
player1.put("home2", new Location(Bukkit.getWorld("world"), 3, 3, 3));
HashMap<String, Location> player2 = new HashMap<String, Location>();
player2.put("home3", new Location(Bukkit.getWorld("world"), 4, 4, 4));
player2.put("home4", new Location(Bukkit.getWorld("world"), 5, 5, 5));
getConfig().set("player1", player1);
getConfig().set("player2", player2);
saveConfig();

*/