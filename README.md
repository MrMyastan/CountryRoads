[![License: MIT](https://img.shields.io/badge/License-MIT-brightgreen.svg)](https://opensource.org/licenses/MIT) [![Tested on: Spigot 1.16.1](https://img.shields.io/badge/Tested%20on-Spigot%201.16.1-yellow.svg)](https://www.spigotmc.org/) [![Language: Java](https://img.shields.io/badge/Language-Java-red)](https://www.java.com/en/) ![API Jar: 1.16.1-R0.1](https://img.shields.io/badge/API%20Jar-1.16.1--R0.1-blueviolet) ![Version: v1.1.1](https://img.shields.io/badge/Version-v1.3-blue) ![Why am I doing this?: I don't know](https://img.shields.io/badge/Why%20am%20I%20making%20these%3F-I%20don't%20know-lightgrey)
# CountryRoads (Take me Home)
A basic Spigot-API Minecraft plugin for a home system.
You can feel free to modify the code for your own use (just make sure to credit me for my code), and use the plugin for your own server!
Any suggestions for improvements or features are welcome!
# Compatibility
I've tested the plugin using a Spigot 1.16.1 server and made the plugin using the 1.16.1-R0.1 Spigot-API jar, and v1.3 and onwards are compiled with Java 8 (before is Java 11)
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
  - removes the home location specified by name, people with the countryroads.manageotherhomes permission can remove other players homes (specified by owner)
  - all players have permission to use this command by default
  - ops have permission to delete other's homes by default
  - usage: /delhome \[name\] (owner)
- in the config you can set max number of homes or set it to 0 or less disables the limit
  - if you lower the limit and a player has more than the new limit they can keep their homes but will have delete homes until they have less than the limit if they want to add a new home
# Notes
If you are using offline mode and for whatever reason one of your players changes their name (and thus their offline mode UUID) you will need to replace their old offline mode UUID with their new offline mode UUID in the config for them to get access to their homes again\
Not sure what will happen if the entry for a person is doubled (ie, using offline mode and someone added home locations after changing their name and then you changed their old UUID to their new UUID so there are two sets of homes under the same UUID), so I would advise merging the two sets of entries
# To-Do
- [x] write this readme
- [x] improve error messages
- [x] comment my dang computing instructions
- [ ] maybe a list homes feature?
- [x] tab completion
- [x] store by UUID?
- [x] system for limiting number of homes
- [ ] api for players number of remaining homes
- [x] put a little color in the error messages
- [ ] ~~error messages: gordon ramsay edition mode~~
- [x] see if i can switch to just set and list instead of extra list in tab completion
- [x] store some frequently used returns in variables
- [ ] players can't override homes with negative homes remaining
