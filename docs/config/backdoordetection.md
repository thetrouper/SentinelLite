# **Backdoor Detection**
Only **YOU** can prevent backdoors. Even though Sentinel's Anti-Backdoor raises the bar for attackers,
it is always good to follow decent security practices.

By utilizing [these guidelines](backdoorprevention.md), you can significantly reduce the risk of getting backdoored.

---

### **If you wish to go the extra mile...**
...Enable Sentinel Anti Nuke's Backdoor Detection.

The **Backdoor Detection System** in Sentinel is designed to protect your server from unauthorized modifications by ensuring the integrity of your server's JAR files. It detects tampering, backdoors, or unexpected changes to critical files and automatically quarantines suspicious files to prevent potential threats.

---

## **Key Features**
1. **Integrity Checks**: Monitors JAR files for unauthorized changes using SHA-256 checksums.
2. **Quarantine System**: Automatically isolates modified or unrecognized JAR files to prevent execution.
3. **Setup Mode**: Temporarily disables checksums to allow safe plugin updates or modifications.
4. **Custom Patched JAR**: Generates a modified server JAR to enforce protection on startup.

---

## **How It Works**
- On initial setup, the system scans all JAR files in your server directory and records their checksums.
- Every server startup, it rechecks these files. If a file’s checksum doesn’t match, it’s quarantined (renamed to `.quarantined`).
- A **patched server JAR** is created to embed Sentinel’s integrity checks into your server’s startup process.

---

## **Setup Guide**

### **Step 1: Enable Backdoor Detection**
1. Open `main-config.json`.
2. Ensure `backdoorDetection.enabled` is set to `true`.
3. Configure other settings as needed:
   ```json
   "backdoorDetection": {
     "enabled": true,
     "setupMode": false,
     "keepSetupMode": false
   }
   ```

---

### **Step 2: Generate the Patched Server JAR**
1. **First Startup**:
    - Start your server. Sentinel will automatically:
        - Create a patched server JAR (e.g., `server.jar-patched`).
        - Generate checksums for all existing JAR files.
    - **Replace your original server JAR** with the patched version (e.g., rename `server.jar-patched` to `server.jar`).

---

### **Step 3: Adding/Updating Plugins (Setup Mode)**
To modify plugins or JAR files without triggering false positives:

1. Enable **Setup Mode** in `main-config.json`:
   ```json
   "setupMode": true
   ```
2. Restart the server. Sentinel will:
    - Create a setup file (`.sentinel/.checksums.setup`).
    - Reset existing checksums.
3. Add/update plugins or JAR files as needed.
4. **Disable Setup Mode** after finishing:
    - Set `setupMode` back to `false`.
    - Restart the server again. Sentinel will re-generate checksums for the new files.

---

## **Troubleshooting**
- **Quarantined Files**:
    - If a legitimate file is quarantined (e.g., `plugin.jar.quarantined`):
        1. Enable `setupMode`.
        2. Restore the file by removing the `.quarantined` suffix.
        3. Restart the server to re-register its checksum.
- **Patchfile Errors**:
    - If you see `Patchfile verified but not deleted`, manually delete `.sentinel/.patched` and restart.
- **Setup Mode Persistence**:
    - If `keepSetupMode` is `true`, setup mode will stay enabled until manually disabled.

---

## **Best Practices**
- **Regular Backups**: Always backup your server before major changes.
- **Monitor Logs**: Check logs for quarantine alerts or checksum mismatches.
- **Update Carefully**: Use `setupMode` when updating plugins to avoid false positives.
- **Know That**: This system is not foolproof. It is mainly designed to prevent script-kiddies from uploading a malicious plugin through PlugMan.
    - Any competent and persistent attacker will quickly figure out what is going on, and bypass it trivially.
    - It's literally as simple as enabling persistent setup mode and forcing a server reboot.
- **Follow**: The [Backdoor Prevention](backdoorprevention.md) guide

---

By following these steps, Sentinel ensures your server remains secure against backdoors and unauthorized modifications. For further assistance, join our [Discord Support Server](https://discord.gg/Xh6BAzNtxY).