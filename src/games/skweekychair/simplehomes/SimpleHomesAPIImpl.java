package games.skweekychair.simplehomes;

import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;

public class SimpleHomesAPIImpl implements SimpleHomesAPI {

    SimpleHomesPlugin plugin;
    ConfigurationSection users;

    public SimpleHomesAPIImpl(SimpleHomesPlugin mainInstance, ConfigurationSection usersSection) {
        plugin = mainInstance;
        users = usersSection;
    }

    @Override
    public void changeNumHomes(UUID uuid, int change) {
        // is this gonna be a race condition?
        String remainingPath = uuid + ".homes-remaining";
        users.set(remainingPath, users.getInt(remainingPath) + change);
        plugin.saveConfig();
    }

    @Override
    public int getNumHomes(UUID uuid) {
        return users.getInt(uuid + ".homes-remaining");
    }
    
}
