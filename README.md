# Sagittarius - Standalone Minecraft Limbo
Sagittarius is a small Minecraft Limbo project which aims to support any version starting from  1.8. This is accomplished by integrating ViaVersion into the server itself.
The name is inspired by the black hole [Sagittarius A*](https://en.wikipedia.org/wiki/Sagittarius_A*) which is also a slight reference to how limbo servers in general work.

## Features
- [x] WorldEdit Schematics
- [x] Support for version 1.8 - 1.20.4 (ViaVersion)
- [x] Supports game modes (Creative, Spectator, ...)
- [x] Supports BungeeCord IP-Forwarding (incl. Skins)
- [x]  Actionbar support
- [x] The connection plugin message can be changed

## Additional help  for modifying the code
If the default version of Sagittarius does not fit your needs, you can modify the source code according to your needs. In this case you may find the wiki helpful as it contains more resources on that specific  topic.

## Using a WorldEdit schematic
During the startup phase, Sagittarius will attempt to load a schematic called "world.schematic".
If you want to use a custom world, you will need to drop your schematic into the server directory and make sure that the file has the correct name.

## Memory requirements
Sagittarius may use more or less memory depending on your world. We have tested the memory consumption with a small schematic, around 45 x 37 blocks in size. A world of this size required at least 25 MB of memory and worked best with values above 50 megabytes.
It is recommended to start with slightly higher values and only decrease it if absolutely necessary.

## Closing Notes
Special thanks to:
- [ViaVersion](https://github.com/ViaVersion/ViaVersion) for making their work on multi-version support accessible under the GPL license so that others can benefit from it as well.
- [wiki.vg]([wiki.vg](https://wiki.vg/Main_Page)) for sharing their resource on the Minecraft protocol.
- MrKavatch for playing a major role in the realization of this project.