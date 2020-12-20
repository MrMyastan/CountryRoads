package games.skweekychair.simplehomes;

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
        
        if (args.length < 1) {
            return false;
        }

        if (sender instanceof Player != true) {
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
    public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        return null;
    }
    
}
