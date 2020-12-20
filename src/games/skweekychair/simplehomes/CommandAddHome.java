package games.skweekychair.simplehomes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandAddHome implements TabExecutor {

    SimpleHomesPlugin mainPlugin;
    FileConfiguration config;

    public CommandAddHome(SimpleHomesPlugin mainInstance) {
        mainPlugin = mainInstance;
        config = mainInstance.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (sender instanceof Player != true) {
            sender.sendMessage("Sorry, but you have to be a player to use this command");
            return false;
        }

        if (args.length < 1) {
            sender.sendMessage("You must specify a name for your new home");
            return false;
        }

        Player toBeOwner = (Player) sender;

        if (!config.isConfigurationSection(toBeOwner.getName())) {
            config.createSection(toBeOwner.getName());
        }

        config.getConfigurationSection(toBeOwner.getName()).set(args[0], toBeOwner.getLocation());;
        mainPlugin.saveConfig();

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        // this just keeps it from suggesting online player names
        List<String> returns = new ArrayList<String>();
        return returns;
    }
    
}
