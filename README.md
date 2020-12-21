[![License: MIT](https://img.shields.io/badge/License-MIT-brightgreen.svg)](https://opensource.org/licenses/MIT) [![Tested on: Spigot 1.16.1](https://img.shields.io/badge/Tested%20on-Spigot%201.16.1-yellow.svg?logo=minecraft)](https://www.spigotmc.org/) [![Languag: Java](https://img.shields.io/badge/Language-Java-red?logo=java)](https://www.java.com/en/) ![API Jar: 1.16.1-R0.1](https://img.shields.io/badge/API%20Jar-1.16.1--R0.1-blueviolet) ![Version: v1.0](https://img.shields.io/badge/Version-v1.0-blue) ![Why am I doing this?: I don't know](https://img.shields.io/badge/Why%20am%20I%20making%20these%3F-I%20don't%20know-lightgrey)
# SimpleHomes
A basic Spigot-API Minecraft plugin for a home system.
You can feel free to modify the code for your own use (just make sure to credit me for my code), and use the plugin for your own server!
Any suggestions for improvements or features are welcome!
# Compatibility
I've tested the plugin using a Spigot 1.16.1 server and made the plugin using the 1.16.1-R0.1 Spigot-API jar
# Features
- home command
  - teleport to your home specified by \[name\] or if no home is specified your home named default
  - all players have permission to use this command by default
  - usage: /home (name)
- addhome command
  - add a home location with the name specified by \[name\]
  - all players have permission to use this command by default
  - usage: /addwhome \[name\]
  - will override an existing home location with the same name
  - a home named default will be the one a player is teleported to if they use /home without specifying which of their homes
- delhome command
  - removes the home location specified by name, people with the simplehomes.manageotherhomes permission can remove other players homes (specified by owner)
  - all players have permission to use this command by default
  - ops have permission to delete other's homes by default
  - usage: /delhome \[name\] (owner)
# Notes
Right now I'm storing home locations under peoples usernames and not their UUIDs, so if someone changes their mc name, they will lose access to their homes, BUT this can be easily remedied by changing their old name to their new name in the plugin's config.yml\
Not sure what will happen if the entry for a person is doubled (ie, they added home locations and then you changed their old name to their new name so there are two sets of homes under the same name), so i would advise merging the two sets of entries\
I'm currently not sure how to switch to UUIDs without breaking offline mode servers but I'll hopefuly get around to figuring it out
# To-Do
- [x] write this readme
- [x] improve error messages
- [ ] comment my dang computing instructions
- [ ] maybe a list homes feature?
- [x] tab completion
- [ ] store by UUID?
- [ ] system for limiting number of homes? an api for that? If making an api for it is possible?
- [ ] put a little color in the error messages (actual color, not swearing, although maybe a swearing mode would be nifty...)
