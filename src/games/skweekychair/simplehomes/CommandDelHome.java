package games.skweekychair.simplehomes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class CommandDelHome implements TabExecutor {

    SimpleHomesPlugin plugin;
    FileConfiguration config;

    public CommandDelHome(SimpleHomesPlugin mainInstance) {
        plugin = mainInstance;
        config = mainInstance.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "You need to at least specify a home to delete");
            return false;
        }

        String owner;

        /* I am aware this is a little spaghetti, but it covers every case that should come up
        
        P = sender is Player
        NP = sender is Non-Player
        SO = Specified Owner
        OU = Owner Unspecified
        
        Possible Cases:
          P NP
        SO 1 2
        OU 3 4

        Cases 1 & 2 are caught by the first if and both work fine
        we now know the owner is unspecified because if there was a second arg it would be caught by the first if
        so case 3 is handled by the next if, and again handled fine
        that leaves only the fourth possible case which is then caught by the else and errored because non players
        (command blocks, terminals) can't have homes
        (or can they? hm.... conversation for another comment) */
        if (args.length >= 2) {
            if (!sender.hasPermission("simplehomes.manageotherhomes")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to delete other people's home locations");
                return false;
            }

            /* instead of getting a uuid from a name regardless if they are on or offline because
            thats hard to do without edge case (or surprisingly large chunk of offline mode server)
            problems, i get the names from all the uuids and compare them to the argument and use the
            one that matches and if no match is found error out
            DISCUSSION POINT: why am i supporting ops managing homes if they dont know what homes people
            have without the conf and if they had the conf they could just manually delete em? 
            OTHER IDEA: seperate managing (listing, deleting, teleporting to) other players homes into a 
            other homes command or smth */
            owner = null;
            for (String key : config.getKeys(false)) {
                String homeHaver = Bukkit.getOfflinePlayer(UUID.fromString(key)).getName();
                if (homeHaver.equals(args[1])) {
                    owner = key;
                    break;
                }
            }

            if (owner == null) {
                sender.sendMessage(ChatColor.RED + "Specified owner does not have a home");
                return false;
            }

        } else if (sender instanceof Player) {
            Player player = (Player) sender;
            owner = player.getUniqueId().toString();
        } else {
            sender.sendMessage(ChatColor.RED + "You must specify who the home belongs to");
            return false;
        }

        String path = owner + "." + args[0];

        if (!config.contains(path)) {
            sender.sendMessage(ChatColor.RED + "Specified home could not be found");
            return false;
        }

        config.set(path, null);
        plugin.saveConfig();

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        
        // see this section in CommandHome
        List<String> names = new ArrayList<String>();
        if (args.length > 2) {return names;}
        
        if (args.length == 1) {
            if (sender instanceof Player != true) {return names;}
            Player teleportee = (Player) sender;

            // see this section in CommandHome
            if (!config.isConfigurationSection(teleportee.getUniqueId().toString())) {return names;}

            // get the names of the player's homes
            Set<String> namesSet = config.getConfigurationSection(teleportee.getUniqueId().toString()).getKeys(false);
            names.addAll(namesSet);
        } else if (args.length == 2) {
            if (!sender.hasPermission("simplehomes.manageotherhomes")) {return names;}
            
            // get the names of players with homes
            for (String key : config.getKeys(false)) {
                String ownerName = Bukkit.getOfflinePlayer(UUID.fromString(key)).getName();
                names.add(ownerName);
            }
        }

        // see this section in CommandHome
        List<String> returns = new ArrayList<String>();
        StringUtil.copyPartialMatches(args[args.length - 1], names, returns);
        Collections.sort(returns);
        return returns;
    }
    
}















/* rant that was previously placed before this block of code: 
owner = null;
for (String key : config.getKeys(false)) {
    String homeHaver = Bukkit.getOfflinePlayer(UUID.fromString(key)).getName();
    if (homeHaver == args[1]) {
        owner = key;
        break;
    }
}

if (owner == null) {
    sender.sendMessage("Specified owner does not have a home");
    return false;
}

at the end of the if block for args.length >= 2
I'm moving the rant here but keeping it for preservations sake:

ah, this section of code.
*sighs*
so, the whole point of this is to handle getting UUIDs of possibly offline players from strings
(sort of)
while still maintaining compatibility with offline mode servers and avoiding deprecated methods 
or me learning how to do extra things like calling the mojang web api and parsing the json or async
so since getOfflinePlayer(String name) is deprecated, either because its possibly blocking due
to not making a web request through async or because "Persistent storage of users should be by 
UUID as names are no longer unique past a single session." (which seems odd to deprecate it for
only one of its possible use cases) depending on if you believe people in spigots discord or their
docs, respectively
(and also since I don't particularly feel like learning the above mentioned stuff right now)
I've decided that instead of getting a uuid from a player object from a player name and then popping 
it in the path with the home name and checking if it exists and all the other stuff i was doing before, 
I've decided to basically do the opposite, get all the uuids with home data, go through each one, 
construct a player object, and check if its name matches the given name
if there is a match, yay! yippee! now this is podracing! woo! continue on as normal
if one was not, error out
this solution although it feels slightly hacky and retrofitted to me does make it easier to figure
out if the supplied username was wrong or home name was wrong, seeing as if the specified user
was the problem it wil tell you that specifically, and otherwise give you the generic one
so i guess maybe i should put this in the case 3 handling code
some alternate solutions to this dilemma would've been to run getOfflinePlayer(String name) async
but the method is still deprecated so i do still want to avoid using it
(although it has been deprecated for 2 years now so ig theres no risk of it being removed soon)
another option wouldve been to call the mojang api for a uuid and if it doesnt have one then
construct an offline uuid which would have the upside of if the player has a standard account
then even if the server is offline mode they dont need to edit their uuid in the yml if they 
change their username buuuut then i would have to use that method of getting uuids whenever
im going to store use because otherwise if the server is running offline mode ill be getting
offline mode uuids from player objects (i think) which then wouldnt match possible online
mode uuids returned from the api servers
i guess i could check if the server was set to online/offline mode but i would also have
to check if its getting online mode uuids forwarded from bungeecord and im sure there are 
like 20000 other edge cases (which i guess there always are but whatever)
so yeah
sorry for the rant
i was just a bit frustrated with this and i was hoping for a simple transition to uuids
please correct me (politely!) if i've said something stupid
and honestly this code is probably the best solution, as far as i can tell there aren't
any weird compatibility problems or edge cases, i don't have to change any other methods
of getting uuids, (i dont have to learn any new technologies,) it gives me better error 
messages and its simple
I'm just slightly annoyed because its feels a bit hacky/retrofitted onto the program flow 
I was going for and because of the lack of a more main stream solution
TL;DR instead of getting a uuid from a name regardless if they are on or offline because
thats hard to do without edge case (or surprisingly large chunk of offline mode server)
problems, i get the names from all the uuids and compare them to the argument and use the
one that matches and if no match is found error out
DISCUSSION POINT: why am i supporting ops managing homes if they dont know what homes people
have without the conf and if they had the conf they could just manually delete em?
maybe this feature is more effort than its worth 

*/