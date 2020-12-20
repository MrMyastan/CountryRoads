package games.skweekychair.simplehomes;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

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
            return false;
        }

        String owner = null;

        if (args.length >= 2) {
            if (!sender.hasPermission("simplehomes.manageotherhomes")) {
                return false;
            }
            owner = args[1];
        } else if (sender instanceof Player) {
            Player player = (Player) sender;
            owner = player.getName();
        }

        if (owner == null) {
            return false;
        }

        String path = owner + "." + args[0];

        if (!config.contains(path)) {
            return false;
        }

        config.set(path, null);
        mainPlugin.saveConfig();

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
