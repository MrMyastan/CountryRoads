package games.skweekychair.simplehomes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class CommandDelHome implements TabExecutor {

    SimpleHomesPlugin mainPlugin;
    FileConfiguration config;

    public CommandDelHome(SimpleHomesPlugin mainInstance) {
        mainPlugin = mainInstance;
        config = mainInstance.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 1) {
            sender.sendMessage("You need to at least specify a home to delete");
            return false;
        }

        String owner = null;

        if (args.length >= 2) {
            if (!sender.hasPermission("simplehomes.manageotherhomes")) {
                sender.sendMessage("You do not have permission to delete other people's home locations");
                return false;
            }
            owner = args[1];
        } else if (sender instanceof Player) {
            Player player = (Player) sender;
            owner = player.getName();
        } else {
            sender.sendMessage("You must specify who the home belongs to");
            return false;
        }

        String path = owner + "." + args[0];

        if (!config.contains(path)) {
            sender.sendMessage("Specified home could not be found");
            return false;
        }

        config.set(path, null);
        mainPlugin.saveConfig();

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        
        List<String> names = new ArrayList<String>();
        if (args.length > 2) {return names;}
        
        if (args.length == 1) {
            if (sender instanceof Player != true) {return names;}
            Player teleportee = (Player) sender;

            if (!config.isConfigurationSection(teleportee.getName())) {return names;}

            Set<String> namesSet = config.getConfigurationSection(teleportee.getName()).getKeys(false);
            names.addAll(namesSet);
        } else if (args.length == 2) {
            if (!sender.hasPermission("simplehomes.manageotherhomes")) {return names;}
            Set<String> namesSet = config.getKeys(false);
            names.addAll(namesSet);
        }

        List<String> returns = new ArrayList<String>();
        StringUtil.copyPartialMatches(args[args.length - 1], names, returns);
        Collections.sort(returns);
        return returns;
    }
    
}
