package games.skweekychair.simplehomes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class CommandHome implements TabExecutor {

    FileConfiguration config;

    public CommandHome(FileConfiguration mainConfig) {
        config = mainConfig;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (sender instanceof Player != true) {
            sender.sendMessage("Sorry, but you have to be a player to use this command");
            return false;
        }
        
        Player teleportee = (Player) sender;

        // Ty Tordek for this clever line of code
        String path = teleportee.getName() + "." + (args.length == 0 ? "default" : args[0]);

        Location toTeleportTo = null;
        toTeleportTo = config.getLocation(path);

        if (toTeleportTo == null) {
            teleportee.sendMessage("Specified home does not exist or you have no default home");
            return false;
        }

        teleportee.teleport(toTeleportTo);

        return true;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String name, String[] args) {
        
        List<String> names = new ArrayList<String>();
        if (args.length > 1 || sender instanceof Player != true) {return names;}

        Player teleportee = (Player) sender;

        if (!config.isConfigurationSection(teleportee.getName())) {return names;}

        Set<String> namesSet = config.getConfigurationSection(teleportee.getName()).getKeys(false);
        names.addAll(namesSet);

        List<String> returns = new ArrayList<String>();
        StringUtil.copyPartialMatches(args[0], names, returns);
        Collections.sort(returns);
        return returns;
    }

    /* 	

        How I implemented tab completion for my warp command

    List<String> names = new ArrayList<String>();
    if (args.length > 1) {return names;}
    Set<String> namesSet = teleportLocations.keySet();
    names.addAll(namesSet);
    List<String> returns = new ArrayList<String>();
    StringUtil.copyPartialMatches(args[0], names, returns);
    Collections.sort(returns);
    return returns;
    */

}