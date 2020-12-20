package games.skweekychair.simplehomes;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

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

        Location toTeleportTo = null;

        if (args.length >= 1) {
            toTeleportTo = config.getLocation(teleportee.getName() + "." + args[0]);
        } else {
            toTeleportTo = config.getLocation(teleportee.getName() + ".default");
        }

        if (toTeleportTo == null) {
            teleportee.sendMessage("Specified home does not exist or you have no default home");
            return false;
        }

        teleportee.teleport(toTeleportTo);

        return true;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        // TODO Auto-generated method stub
        return null;
    }

}