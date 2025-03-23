# Chat Filtering System

Sentinel's chat filtering system is designed to automatically detect and mitigate inappropriate chat content, spam, and other violations. This document explains the key components and mechanics of the system.

---

## Filtering Process
There are currently 4 chat filtering steps.

### **1. Unicode Filtering**
Messages are checked for any characters not present on the QWERTY keyboard.

- Its default regex is `[^A-Za-z0-9\\[,./?><|\\]§()*&^%$#@!~{}:;'\"-_ ]`
- You can configure it in the `main-config.json`.

### **2. Profanity Filter**
Messages undergo multiple processing steps to detect profanity:

1. **Lowercase Conversion**
2. **False Positive Removal** (e.g., whitelisted phrases).
3. **Leetspeak Conversion** (e.g., `h3ll0` → `hello`).
4. **Special Character Removal** (e.g., `h!e$l` → `hel`).
5. **Repeating Letter Simplification** (e.g., `heeeello` → `helo`).
6. **Punctuation Removal** (e.g., `h.e.l.l.o` → `hello`).

At each step, the system checks for profanity with increasing severity. If detected:
- The message is blocked.
- The player's **profanity score** increases.

#### **Profanity Score**
A numerical value assigned to players based on the amount of detected profanity in their messages.

- **How it works:**
    - Each profanity detection has a `Severity` level (e.g., `LOW`, `MEDIUM`, `HIGH`). 
    - Severity is based on how the player tried to bypass the filter, **not the context of the profanity.**
    - Higher severity levels contribute more to the player's total score.
    - Scores accumulate over time and decay gradually via `scoreDecay` (configurable).
- **Auto-Punishment:**
    - Players are punished if their score exceeds the `punishScore` threshold (configurable).
    - `SLUR` triggers **immediate punishment**, bypassing the score threshold.
    - You can add slurs in its config file (`strict.json`), or the GUI.

---

### **3. Spam Filter**
Messages are checked for spam-like behavior:
1. **Similarity Check**  
   Compares the current message to the player's last message.
    - Similarity ≥ `blockSimilarity` → Blocked immediately.
    - Lower similarity values add heat based on configured tiers.
2. **Heat Accumulation**  
   Heat increases based on similarity. Repeated violations push the player closer to punishment thresholds.

#### **Spam Heat**
A numerical value representing a player's likelihood of spamming, based on message similarity and frequency.

- **How it works:**
    - Heat increases when messages are similar to previous ones (calculated via similarity percentage).
    - Higher similarity results in more heat added (configurable: `lowGain`, `mediumGain`, `highGain`).
    - Heat decays over time via `heatDecay` (configurable).
- **Auto-Punishment:**
    - Messages are blocked if heat exceeds `blockHeat` (configurable).
    - Players are punished if heat exceeds `punishHeat` (configurable).
    - Messages with similarity above `blockSimilarity` are blocked immediately.

---

### **4. URL Filter**
Sentinel allows the filtering of URLS in your chat, only letting through authorized links.

- Blocks or punishes players for sending URLs in chat.
- The Default URL regex is `\\b(?:(?:https?|ftp):\\/\\/)?(?:\\S+(?::\\S*)?@)?(?:(?!(?:10|127)(?:\\.\\d{1,3}){3})(?!(?:169\\.254|192\\.168)(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)(?:\\.(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)*(?:\\.(?:com|org|net|int|edu|gov|mil|arpa|biz|info|mobi|app|name|aero|jobs|museum|travel|a[c-gil-oq-uwxz]|b[abd-jmnoq-tvwyz]|c[acdf-ik-orsu-z]|d[dejkmoz]|e[ceghr-u]|f[ijkmor]|g[abd-ilmnp-uwy]|h[kmnrtu]|i[delmnoq-t]|j[emop]|k[eghimnprwyz]|l[abcikr-vy]|m[acdeghk-z]|n[acefgilopruz]|om|p[ae-hk-nrstwy]|qa|r[eosuw]|s[a-eg-or-vxyz]|t[cdfghj-prtvwz]|u[agksyz]|v[aceginu]|w[fs]|y[etu]|z[amrw])))(?::\\d{2,5})?(?:\\/\\S*)?\\b`
- This regex should minimize false positives due to players using punctuation.
- You can configure it in the `main-config.json`.
- In the URL Filter section of the main config, you will also find the link whitelist, which you can use to allow players to mention your own store or website in the chat.

## Auto-Punishment System
The auto-punishment system is designed to minimize false punishments.

1. **Detection**
    - Profanity or spam is detected via the above filters.
2. **Scoring/Heating**
    - The player's score or heat is updated.
3. **Threshold Check**
    - If the score exceeds `punishScore` or heat exceeds `punishHeat`, punishment commands (configurable) are executed.
    - Example command: `mute %player% 30m do not swear in chat.`.
4. **Decay**
    - Scores and heat decrease over time to forgive older violations.

---

## Configuration Overview
### Key Configurable Values:
Configuration of the chat filter is done through the GUI (`/sentinel config`) or through the `main-config.json` in the plugin's data folder.

- **Global Config**
    - Main toggling: Each filter can be turned on or off individually.
    - Silent mode: Each filter can be set to flag silently, making it appear to the violator as though their message was sent. This is a powerful tool for discouraging bypass attempts, although it can lead to some confusion when players get ignored.
    - Punishment toggle: Filters can be configured to not run the punishment commands, although the same effect can be achieved by just removing all punishment commands.
    - Punishment Commands: Every filter has a unique list of punishment commands, you can use the placeholder `%player%` to denote the username of who flagged.
- **Profanity Filter**
    - Severity scores (`lowScore`, `mediumScore`, `highScore`, etc.).
    - `punishScore`: Threshold for punishment.
    - `scoreDecay`: Rate at which scores decrease.
- **Spam Filter**
    - `blockSimilarity`: Similarity percentage to auto-block.
    - `blockHeat`/`punishHeat`: Heat thresholds for blocking/punishing.
    - `heatDecay`: Rate at which heat decreases.

---

## Player Experience
- **Warnings**
    - Players receive warnings when their messages are blocked.
    - Staff members see detailed reports with violation context.
- **Logging**
    - All violations are logged in chat for all staff members and to console.
    - If a violation results in a punishment, it will get logged to discord too.
- **Transparency**
    - Players can appeal punishments via a clickable in-game report system (unless you have silent mode enabled).

---

This system ensures a balance between automated enforcement and configurability, allowing server admins to tailor it to their community's needs.