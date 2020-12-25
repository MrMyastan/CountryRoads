package games.skweekychair.simplehomes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandAddHome implements TabExecutor {

    SimpleHomesPlugin plugin;
    FileConfiguration config;

    public CommandAddHome(SimpleHomesPlugin mainInstance) {
        plugin = mainInstance;
        config = mainInstance.getConfig();
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

        // if the player doesn't have a section for storing their homes then create one so we don't
        // get an NPE trying to set a value in it
        if (!config.isConfigurationSection(toBeOwner.getUniqueId().toString())) {
            config.createSection(toBeOwner.getUniqueId().toString());
        }

        // save the new home location in the players storage section
        config.getConfigurationSection(toBeOwner.getUniqueId().toString()).set(args[0], toBeOwner.getLocation());
        plugin.saveConfig();

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        // this just keeps it from suggesting online player names
        List<String> returns = new ArrayList<String>();
        return returns;
    }
    
}
