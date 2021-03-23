package games.skweekychair.simplehomes;

import java.util.UUID;

public interface SimpleHomesAPI {
    void changeNumHomes(UUID uuid, int change);

    int getNumHomes(UUID uuid);
}
