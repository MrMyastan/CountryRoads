package games.skweekychair.countryroads;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

public class CountryRoadsAPIImpl implements CountryRoadsAPI {

    CountryRoadsPlugin plugin;
    ConfigurationSection users;

    public CountryRoadsAPIImpl(CountryRoadsPlugin mainInstance, ConfigurationSection usersSection) {
        plugin = mainInstance;
        users = usersSection;
    }

    @Override
    public boolean changeHomesRemaining(UUID uuid, int change) {
        if (!users.isConfigurationSection(uuid.toString())) {
            return false;
        }
        
        // is this gonna be a race condition?
        String remainingPath = uuid + ".homes-remaining";
        users.set(remainingPath, users.getInt(remainingPath) + change);
        plugin.saveConfig();
        return true;
    }

    @Override
    public Integer getHomesRemaining(UUID uuid) {
        if (!users.isInt(uuid + ".homes-remaining")) {
            return null;
        }
        
        return Integer.valueOf(users.getInt(uuid + ".homes-remaining"));
    }

    @Override
    public boolean setHomesRemaining(UUID uuid, int homes) {
        if (!users.isConfigurationSection(uuid.toString())) {
            return false;
        }
        
        users.set(uuid + ".homes-remaining", homes);
        plugin.saveConfig();
        return true;
    }

    @Override
    public Map<String, Location> getUserHomes(UUID uuid) {
        if (!users.isConfigurationSection(uuid.toString())) {
            return null;
        }
        
        HashMap<String, Location> homes = new HashMap<String, Location>();
        ConfigurationSection homesSection = users.getConfigurationSection(uuid + ".homes");

        for (String key : homesSection.getKeys(false)) {
            // I'm ignoring invalid locations, should i be doing that?
            if (homesSection.isLocation(key)) {
                homes.put(key, homesSection.getLocation(key));
            }
        }

        return homes;
    }

    @Override
    public Set<UUID> getRegisteredUUIDS() {
        Set<UUID> uuids = new HashSet<UUID>();

        for (String key : users.getKeys(false)) {
            uuids.add(UUID.fromString(key));
        }
        
        return uuids;
    }

    @Override
    public int getInitialHomes() {
        return users.getParent().getInt("initial-homes");
    }

}
