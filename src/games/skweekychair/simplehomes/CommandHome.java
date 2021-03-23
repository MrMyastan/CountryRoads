package games.skweekychair.simplehomes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class CommandHome implements TabExecutor {

    ConfigurationSection homes;

    public CommandHome(ConfigurationSection mainHomes) {
        homes = mainHomes;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        // yes i am aware this != true is ugly but would you rather have (!(...)) or an extra layer of nesting
        if (sender instanceof Player != true) {
            sender.sendMessage(ChatColor.RED + "Sorry, but you have to be a player to use this command");
            // Not a syntax problem so why return false to display usage?
            return true;
        }
        
        Player teleportee = (Player) sender;

        // Ty Tordek for this clever line of code
        String path = teleportee.getUniqueId().toString() + ".homes." + (args.length == 0 ? "default" : args[0]);

        Location toTeleportTo = homes.getLocation(path);

        if (toTeleportTo == null) {
            teleportee.sendMessage(ChatColor.RED + "Specified home does not exist or you have no default home");
            return false;
        }

        teleportee.teleport(toTeleportTo);

        return true;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String name, String[] args) {
        
        /* There doesn't seem to be a way to indicate erroneous or extra command arguments (or at least one
        that doesnt require a bunch of effort like hooking into brigadier) so i return an empty list of
        auto fills because returning null makes it use the default auto complete method which is names of
        online players */

        List<String> returns = new ArrayList<String>();
        if (args.length > 1 || sender instanceof Player != true) {return returns;}

        Player teleportee = (Player) sender;
        String teleporteeUUIDStr = teleportee.getUniqueId().toString() + ".homes";

        // if theres no section for storing the players homes, then return no home names, also convenietly makes
        // sure there is a config so we dont get an NPE when we get the keys (home names) for the players section
        if (!homes.isConfigurationSection(teleporteeUUIDStr)) {return returns;}

        // get the names of all the players homes
        Set<String> names = homes.getConfigurationSection(teleporteeUUIDStr).getKeys(false);

        /* I dont think I can copy the partial matches back into itself, so construct a new list!
        also I've been doing it like this cuz thats how I saw someone do it on the forums but I
        think I can copy the partial matches of the set into the list so then I'll only need 
        one list and dont have to addAll, so I should get around to trying that */
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