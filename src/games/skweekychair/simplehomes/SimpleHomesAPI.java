package games.skweekychair.simplehomes;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;

/**
 * The API class through which you can interact with my glorious, definitely
 * not terrible, plugin
 */
public interface SimpleHomesAPI {
    /**
     * Adds [change] number of homes to the player specified by 
     * uuid. It is possible for a player to have a negative number of homes 
     * remaining in which case they can only replace homes or remove them 
     * until they have spare homes. 
     * @param uuid 
     * The UUID of the player to apply the change to
     * @param change
     * The number of homes to add, can be negative to remove homes
     * @return
     * true if the change succeeded, false if the player's data could 
     * not be found
     */
    boolean changeHomesRemaining(UUID uuid, int change);

    /**
     * Gets the number of homes remaining for a player
     * @param uuid
     * The UUID of the player
     * @return
     * The number of homes remaining or null if the player's data can't be 
     * found. If the player has not added a home before it won't be in the 
     * store and will return null.
     */
    Integer getHomesRemaining(UUID uuid);

    /**
     * Sets the remaining homes for a player
     * @param uuid
     * The UUID of the player
     * @param homes
     * What to set their remaining homes to
     * @return
     * true on success, false if the specified player's data couldn't be found
     */
    boolean setHomesRemaining(UUID uuid, int homes);

    /**
     * Gets the homes of a player
     * @param uuid
     * The UUID of the player
     * @return
     * A map of home names to their locations or null if the player's data 
     * could not be found
     */
    Map<String, Location> getUserHomes(UUID uuid);

    /**
     * Gets all of the UUIDs we are storing data for
     * @return
     * A set of player UUIDs
     */
    Set<UUID> getRegisteredUUIDS();

    /**
     * Gets the initial number of homes given to a player
     * <p>
     * The homes are allocated when they first create a home (also when they 
     * are first entered into the data store)
     * @return
     * The initial number of homes, 0 or less disables home limiting
     */
    int getInitialHomes();

}
