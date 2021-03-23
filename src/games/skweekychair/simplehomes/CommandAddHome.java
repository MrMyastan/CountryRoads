package games.skweekychair.simplehomes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class CommandAddHome implements TabExecutor {

    SimpleHomesPlugin plugin;
    ConfigurationSection players;

    public CommandAddHome(SimpleHomesPlugin mainInstance, ConfigurationSection playersSection) {
        plugin = mainInstance;
        players = playersSection;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (sender instanceof Player != true) {
            sender.sendMessage(ChatColor.RED + "Sorry, but you have to be a player to use this command");
            // Not a syntax problem so why return false to display usage?
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "You must specify a name for your new home");
            return false;
        }

        Player toBeOwner = (Player) sender;
        String toBeOwnerUUIDStr = toBeOwner.getUniqueId().toString();

        int maxHomes = plugin.getConfig().getInt("initial-homes");

        // if the player doesn't have a section for storing their homes then create one so we don't
        // get an NPE trying to set a value or checking num of homes in it
        if (!players.isConfigurationSection(toBeOwnerUUIDStr)) {
            players.createSection(toBeOwnerUUIDStr).set("homes-remaining", maxHomes);
            players.getConfigurationSection(toBeOwnerUUIDStr).createSection("homes");
        }

        ConfigurationSection userHomes = players.getConfigurationSection(toBeOwnerUUIDStr);
        String path = "homes." + args[0];
        
        if (userHomes.isLocation(path)) {
            userHomes.set(path, toBeOwner.getLocation());
            plugin.saveConfig();
            sender.sendMessage(ChatColor.GREEN + "Home \"" + args[0] + "\" overridden!");
            return true;
        }
        
        if (maxHomes > 0 && userHomes.getInt("homes-remaining") <= 0) {
            sender.sendMessage(ChatColor.RED + "You have hit the limit for number of homes, delete one or override one");
            return true;
        }

        userHomes.set(path, toBeOwner.getLocation());
        plugin.saveConfig();

        if (maxHomes > 0) {
            int homesRemaining = userHomes.getInt("homes-remaining") - 1;
            userHomes.set("homes-remaining", homesRemaining);
            plugin.saveConfig();
            ChatColor numHomesStatus = homesRemaining == 0 ? ChatColor.YELLOW : ChatColor.GREEN;
            sender.sendMessage(numHomesStatus + "You now have " + homesRemaining + " home(s) remaining");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        // this just keeps it from suggesting online player names
        List<String> returns = new ArrayList<String>();
        return returns;
    }
    
}
