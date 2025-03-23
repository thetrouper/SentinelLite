# Command Block Whitelisting

The Command Block Whitelisting system allows server administrators to protect command blocks from unauthorized modifications. 
Whitelisted command blocks are tracked, managed, and restored if tampered with. 
Command blocks that are not whitelisted will be prevented from executing, and destroyed.
This guide explains how to use all features of the system.

---

## Getting Started
### Permissions
- [Trust](../concepts/trust.md) is required.
- Permission: `sentinel.staff` is required.

### Obtaining the Wand
Use the wand to interact with command blocks and selections:
```bash
/sentinel wand
```
This gives you the **Command Block Wand**, which highlights nearby command blocks and allows area selection.
While the wand isn't strictly necessary, it drastically simplifies the process.
---

## Using the Wand
### Key Bindings
- **Left-Click Block**: Set Position 1 / Remove from Whitelist (if targeting a command block).
- **Right-Click Block**: Set Position 2 / Add to Whitelist (if targeting a command block).
- **Sneak + Left/Right Click**: Force-set positions (bypasses command block detection).
- **Break Command Block**: Remove it from the whitelist.

### Highlight Colors
- **Red**: Not whitelisted.
- **Green**: Whitelisted.
- **Blue**: Your selection area.
- **Purple**: Missing command block (whitelisted but not present).
- **Black**: Unidentified (the wand will correct this automatically).

---

## Command Usage
### Main Command Structure
```
/sentinel commandblock <subcommand> [args]
```

### Subcommands
#### 1. **Selection Management**
Define a region to batch-manage command blocks:
```
/sentinel commandblock selection <action>
```
- Actions:
    - `pos1`/`pos2`: Set selection boundaries.
    - `add`: Whitelist all command blocks in the selection.
    - `remove`: Un-whitelist all command blocks in the selection.
    - `delete`: Delete all command blocks in the selection.
    - `deselect`: Clear your current selection.

#### 2. **Whitelist Single Blocks**
- **Add a command block** you’re looking at:
  ```
  /sentinel commandblock add
  ```
- **Remove a command block** you’re looking at:
  ```
  /sentinel commandblock remove
  ```

#### 3. **Auto-Whitelist Mode**
Toggle automatic whitelisting for placed/edited command blocks:
```
/sentinel commandblock auto
```

#### 4. **Bulk Actions**
- **Restore** all whitelisted command blocks:
  ```
  /sentinel commandblock restore all
  ```
- **Restore** a player’s whitelisted blocks:
  ```
  /sentinel commandblock restore <player>
  ```
- **Clear** all whitelisted blocks:
  ```
  /sentinel commandblock clear all
  ```
- **Clear** a player’s whitelisted blocks:
  ```
  /sentinel commandblock clear <player>
  ```

---

## GUI Management
Open the main GUI:
```
/sentinel config
```
Click on the Command Block Whitelist item.
### Features
1. **Filter Options**:
    - Filter by owner, world, type (minecart/chain/repeating), whitelist status, and more.
    - Right-click filters to set specific values (e.g., player names).

2. **Block Actions**:
    - **Teleport**: Jump to the block’s location.
    - **Toggle Whitelist**: Enable/disable protection.
    - **Restore**: Rebuild missing command blocks.
    - **Destroy**: Remove the block (shift-click).
    - **Take Ownership**: Claim control of the block.

---

## Auto-Whitelist Mode
When enabled (**/sentinel commandblock auto**):
- Newly placed command blocks are automatically whitelisted.
- Edits to existing command blocks (e.g., command changes) update the whitelist entry.

---

## Tips
- Use the wand’s selection to mass-manage command blocks in large builds.
- The GUI’s "Missing Command Blocks" filter helps locate whitelisted blocks that were destroyed.
- Combine `auto` mode with the wand for seamless protection during construction.

---

## Support
For issues, use `/sentinel debug toggle` to enable debug logs and contact the plugin developer.