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
    ConfigurationSection homes;

    public CommandAddHome(SimpleHomesPlugin mainInstance) {
        plugin = mainInstance;
        homes = mainInstance.getConfig().getConfigurationSection("homes");
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

        // if the player doesn't have a section for storing their homes then create one so we don't
        // get an NPE trying to set a value or checking num of homes in it
        if (!homes.isConfigurationSection(toBeOwnerUUIDStr)) {
            homes.createSection(toBeOwnerUUIDStr);
        }

        ConfigurationSection userHomes = homes.getConfigurationSection(toBeOwnerUUIDStr);
        
        int maxHomes = plugin.getConfig().getInt("max-number-of-homes");
        if (maxHomes > 0 && userHomes.getKeys(false).size() >= maxHomes) {
            sender.sendMessage(ChatColor.RED + "You have hit the limit for number of homes, delete one or override one");
            return true;
        }

        // save the new home location in the players storage section
        userHomes.set(args[0], toBeOwner.getLocation());
        plugin.saveConfig();

        if (maxHomes > 0) {
            int numHomes = userHomes.getKeys(false).size();
            ChatColor numHomesStatus = maxHomes - numHomes == 0 ? ChatColor.YELLOW : ChatColor.GREEN;
            sender.sendMessage(numHomesStatus + "You now have " + (maxHomes - numHomes) + " home(s) remaining");
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
