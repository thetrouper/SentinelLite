# General Protections
Sentinel Anti Nuke provides various security checks to prevent malicious activities on a Minecraft server. Below is a list of all available checks, organized by category, along with their descriptions:

---

## **Command Block Checks**
- **Command Block Break**: Monitors and prevents unauthorized breaking of command blocks.
- **Command Block Edit**: Detects and blocks unauthorized modifications to command block commands or settings.
- **Command Block Place**: Restricts players from placing command blocks without permission.
- **Command Block Use**: Tracks and prevents unauthorized activation/use of command blocks.
- **Command Block Execute**: Controls execution of commands via command blocks, ensuring only whitelisted ones run.

---

## **Jigsaw Block Checks**
- **Jigsaw Block Break**: Prevents breaking jigsaw blocks, which are used for structure generation.
- **Jigsaw Block Place**: Restricts placement of jigsaw blocks.
- **Jigsaw Block Use**: Monitors interactions with jigsaw blocks to prevent unintended structure manipulation.

---

## **Structure Block Checks**
- **Structure Block Break**: Detects and blocks attempts to break structure blocks.
- **Structure Block Place**: Restricts placement of structure blocks.
- **Structure Block Use**: Prevents unauthorized saving, loading, or editing of structures via structure blocks.

---

## **Command Minecart Checks**
- **Command Minecart Break**: Monitors breaking of command minecarts (minecarts with command blocks).
- **Command Minecart Place**: Restricts placement of command minecarts.
- **Command Minecart Use**: Detects activation of command minecarts.
- **Command Minecart Edit**: Prevents modifications to command minecart commands or settings.

---

## **Command Execution Checks**
- **Dangerous Commands**: Flags execution of potentially harmful commands (e.g., `op`, `stop`, `ban`).
- **Specific Commands**: Monitors the use of plugin specific commands, (e.g., `minecraft:op`, `luckperms:lp`, `spark:spark`)
- **Logged Commands**: Logs execution of specific commands to Discord or server logs without punishment.

---

## **Creative Mode Exploit Checks**
- **Creative Hotbar (NBT Item Pull)**: Prevents players in creative mode from obtaining items with unauthorized NBT data (e.g., enchanted or modified items).

---

## Configuration Options for Checks
Each check can be configured with the following settings:

- **Enable/Disable**: Toggle the check on or off.
- **De-Op**: Remove operator privileges from violating players.
- **Log to Discord**: Send violation alerts to a Discord channel.
- **Punish**: Execute punishment commands (e.g., kick, ban) when triggered.
- **Custom Commands**: Define commands to run when a violation occurs.

*Available settings may vary based on context*

---

Use the in-game GUI (`/sentinel config`) to customize these checks and adjust their actions based on your server's needs.