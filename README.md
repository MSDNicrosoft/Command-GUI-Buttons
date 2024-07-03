<div align="right">
  English
  |
  <a title="简体中文" href="./README_ZH_CN.md">简体中文</a>
</div>

# Command GUI Buttons

> Fork from https://github.com/joseph-garcia/command-gui-buttons

[![Last build](https://img.shields.io/github/actions/workflow/status/MSDNicrosoft/Command-GUI-Buttons/Build.yml?label=Last%20build&style=flat-square)](https://github.com/MSDNicrosoft/Command-GUI-Buttons/workflows/Build.yml)
[![Github Release](https://img.shields.io/github/v/release/MSDNicrosoft/Command-GUI-Buttons?label=Github%20Release&style=flat-square)](https://github.com/MSDNicrosoft/Command-GUI-Buttons/releases)

## Description

Command GUI Buttons is a fabric mod for Minecraft that allows users to create buttons on their clients
for preset chat messages, commands such as `/warp home`, or anything in the chat box.
Users can bring up this menu in-game with the key <kbd>G</kbd>, type in the button's Name and Command they want to
create, and press the button `+` to create. Then, the button will appear on users' screen for use whenever they want.


## Dependencies

| Dependency | Type     | Link                                                                                                                                                           |
|------------|----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Fabric API | Required | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/fabric-api) \| [Modrinth](https://modrinth.com/mod/fabric-api/)                                      | 
| LibGui     | Required | [GitHub](https://github.com/CottonMC/LibGui/releases) \| [Maven(Not recommend)](https://server.bbkr.space/artifactory/libs-release/io/github/cottonmc/LibGui/) |

For your convenience, download links are listed below:

| Minecraft Version | Fabric API                                                                                                                                                      | LibGui (**Please use the given version**)                             |
|-------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------|
| 1.16.5            | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/fabric-api/files?version=1.16.5) \| [Modrinth](https://modrinth.com/mod/fabric-api/versions?g=1.16.5) | 3.4.0 [GitHub](https://github.com/CottonMC/LibGui/releases/tag/3.4.0) |
| 1.17.1            | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/fabric-api/files?version=1.17.1) \| [Modrinth](https://modrinth.com/mod/fabric-api/versions?g=1.17.1) | 4.2.3 [GitHub](https://github.com/CottonMC/LibGui/releases/tag/4.2.3) |
| 1.18.2            | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/fabric-api/files?version=1.18.2) \| [Modrinth](https://modrinth.com/mod/fabric-api/versions?g=1.18.2) | 5.4.2 [GitHub](https://github.com/CottonMC/LibGui/releases/tag/5.4.2) |
| 1.19.2            | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/fabric-api/files?version=1.19.2) \| [Modrinth](https://modrinth.com/mod/fabric-api/versions?g=1.19.2) | 6.4.0 [GitHub](https://github.com/CottonMC/LibGui/releases/tag/6.4.0) |
| 1.19.3            | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/fabric-api/files?version=1.19.3) \| [Modrinth](https://modrinth.com/mod/fabric-api/versions?g=1.19.3) | 6.5.3 [GitHub](https://github.com/CottonMC/LibGui/releases/tag/6.5.3) |
| 1.19.4            | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/fabric-api/files?version=1.19.4) \| [Modrinth](https://modrinth.com/mod/fabric-api/versions?g=1.19.4) | 7.1.1 [GitHub](https://github.com/CottonMC/LibGui/releases/tag/7.1.1) |
| 1.20.6       | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/fabric-api/files?version=1.20.6) \| [Modrinth](https://modrinth.com/mod/fabric-api/versions?g=1.20.6) | 10.0.0 [GitHub](https://github.com/CottonMC/LibGui/releases/tag/10.0.0) |
| 1.21       | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/fabric-api/files?version=1.21) \| [Modrinth](https://modrinth.com/mod/fabric-api/versions?g=1.21) | 11.0.1 [GitHub](https://github.com/CottonMC/LibGui/releases/tag/11.0.1) |

## Features

- Unlimited number of buttons
- Multiple commands or chat messages in one button
- Custom order execute commands or send messages
- Search for buttons (**Case sensitive**)

![Main Page](./.github/preview/main_page.png)
![Command Edit](./.github/preview/command.png)
![Message Edit](.github/preview/message.png)

## How to Use

- Bring up the menu in-game with the <kbd>G</kbd> key
- Type in the Name and Command you want to save into a button
- Press the `+` button

### How It Works

The list of commands are serialized into a JSON format and stored locally on the users’ systems. The mod writes any new
commands into the JSON as new buttons are created, and loads the JSON at the next start of the game.

## Development

## Support

Current main development for Minecraft version: `1.21`

And use `preprocess` to be compatible with the following versions.

**Note: We only accept the following versions of issues.**

**Please note that this information is time-sensitive and any version of the issue not listed here will be closed.**

- Minecraft `1.16.5`
- Minecraft `1.17.1`
- Minecraft `1.18.2`
- Minecraft `1.19.2`
- Minecraft `1.19.3`
- Minecraft `1.19.4`
- Minecraft `1.20.6`
- Minecraft `1.21`

### Mappings

We are using the **Mojang official** mappings to de-obfuscate Minecraft and insert patches.

### Plan(s)

- [x] Use the `preprocess` to support low Minecraft versions
