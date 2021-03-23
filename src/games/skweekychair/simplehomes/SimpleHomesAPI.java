package games.skweekychair.simplehomes;

import java.util.UUID;

import org.bukkit.OfflinePlayer;

public interface SimpleHomesAPI {
    void changeNumHomes(UUID uuid, int change);
    void changeNumHomes(OfflinePlayer player, int change);

    int getNumHomes(UUID uuid);
    int getNumHomes(OfflinePlayer player);
}
