[![License: MIT](https://img.shields.io/badge/License-MIT-brightgreen.svg)](https://opensource.org/licenses/MIT) [![Tested on: Spigot 1.16.1](https://img.shields.io/badge/Tested%20on-Spigot%201.16.1-yellow.svg?logo=minecraft)](https://www.spigotmc.org/) [![Languag: Java](https://img.shields.io/badge/Language-Java-red?logo=java)](https://www.java.com/en/) ![API Jar: 1.16.1-R0.1](https://img.shields.io/badge/API%20Jar-1.16.1--R0.1-blueviolet) ![Version: v1.0](https://img.shields.io/badge/Version-v1.0-blue) ![Why am I doing this?: I don't know](https://img.shields.io/badge/Why%20am%20I%20making%20these%3F-I%20don't%20know-lightgrey)\
Well... shit. Turns out there is already a bukkit plugin called simplehomes designed for this exact purpose... looks like i need a new name!
# SimpleHomes
A basic Spigot-API Minecraft plugin for a home system.
You can feel free to modify the code for your own use (just make sure to credit me for my code), and use the plugin for your own server!
Any suggestions for improvements or features are welcome!
# Compatibility
I've tested the plugin using a Spigot 1.16.1 server and made the plugin using the 1.16.1-R0.1 Spigot-API jar, and it was compiled with Java 11 (I should probably create a Java 8 version...)
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
If you are using offline mode and for whatever reason one of your players changes their name (and thus their offline mode UUID) you will need to replace their old offline mode UUID with their new offline mode UUID in the config for them to get access to their homes again\
Not sure what will happen if the entry for a person is doubled (ie, using offline mode and someone added home locations after changing their name and then you changed their old UUID to their new UUID so there are two sets of homes under the same UUID), so I would advise merging the two sets of entries\
# To-Do
- [x] write this readme
- [x] improve error messages
- [x] comment my dang computing instructions
- [ ] maybe a list homes feature?
- [x] tab completion
- [x] store by UUID?
- [ ] system for limiting number of homes? an api for that? If making an api for it is possible?
- [x] put a little color in the error messages
- [ ] ~~error messages: gordon ramsay edition mode~~
- [x] see if i can switch to just set and list instead of extra list in tab completion
- [x] store some frequently used returns in variables
# Notes on the API idea
It looks like the best way for me to implement an API for a number of homes thing or whatever for this plugin would be to define an interface and an implementation, in my plugin register the implementation (provider) for the interface (service) with Spigot's service manager, and then distribute the the interface as a jar or whatever that people can import in their project and set their plugin to depend on mine. It seems like a clean and simple way to allow plugins to integrate with mine and means they dont have to import all my source (although thats not really a lot). Buuuuuut I don't know if this is exactly the intended usage of the services manager which seems to be more for like general services like "permission manager" or "currency database" or whatever. On the other hand though, it seems like it would be simpler than implementing my own spigot-like plugin loader or whatever and it hopefully eliminates the chances of getting flamed for static abuse sooooooooo.
