# Backdoor Prevention
## **🎮 Hosting Provider Security** (For Those Using a Shared Host or Panel Like Apex, PebbleHost, Shockbyte, etc.)
These steps are for users who rent Minecraft servers from a hosting provider and manage them through a web panel.

### ✅ **Things to Do**
1. **Use a Hosting Provider with DDoS Protection** – Some budget hosts lack good DDoS mitigation, so choose wisely.
2. **Set Strong Panel Passwords** – Use a **unique, strong password** for your control panel (Multicraft, Pterodactyl, etc.) and enable 2FA if available.
3. **Be Cautious with Plugin Uploads** – Only install plugins from trusted sources like [SpigotMC](https://www.spigotmc.org/), [Bukkit](https://dev.bukkit.org/), [PaperMC](https://hangar.papermc.io/) or [Modrinth](https://modrinth.com/plugins).
4. **Limit OP and Console Access** – Don’t give out OP privileges unless absolutely necessary.
5. **Use a Permissions Plugin (Like [LuckPerms](https://luckperms.net/))** – Instead of giving OP, set up proper ranks and permissions. 
6. **Monitor Player Activity and Logs** – Regularly check logs (`latest.log`, `server.log`) for unusual activity.
7. **Enable Backups If Your Host Provides Them** – Some hosts offer automatic backups; enable them in case of an attack. Review the [3-2-1 Backup Rule](https://www.techtarget.com/searchdatabackup/definition/3-2-1-Backup-Strategy).
    - Make sure backups are stored **outside** your server's filesystem.
8. **Whitelist Your Server If It’s Private** – If you don’t want random players joining, use `/whitelist on`.
9. **Disable Command Blocks If Not Needed** – They can be used for malicious exploits if left enabled. Sentinel offers multiple protections against command blocks, but if you don't need them, don't enable them.
10. **Enable Backups If Your Host Provides Them** – Some hosts offer automatic backups; enable them in case of an attack. Review the [3-2-1 Backup Rule](https://www.techtarget.com/searchdatabackup/definition/3-2-1-Backup-Strategy).
11. **Backups.** - Use them.
12. **Do I have to tell you again?** - Use backups. They will save you countless headaches.

### ❌ **Things to Avoid**
1. **Don’t Share Panel Logins** – Even with friends, everyone should have their own account with proper permissions.
2. **Avoid Sketchy Hosts That Offer “Unlimited” Everything for Cheap** – They often cut corners on security.
3. **Don’t Trust Random People Who Offer to “Help” Manage Your Server** – Many backdoors start with social engineering.
4. **Never Download Leaked/Cracked Plugins** – These often contain malware or hidden backdoors.
5. **Don’t Assume the Host Will Handle All Security** – Even managed services require some security awareness.

---

## **🛠️ Advanced Self-Hosting Security** (For Those Running Their Own Hardware/VPS)
These are more technical steps for users who manage their own Minecraft server on a VPS, dedicated server, or home network.

### ✅ **Things to Do**
1. **Run the Server as a Non-Root User** – Never run your Minecraft server as root; create a dedicated user with limited privileges.
2. **Use a Firewall (UFW, IPTables, or Cloud Firewalls)** – Allow only necessary ports (e.g., 25565 for Minecraft, 22 for SSH with restrictions).
3. **Secure SSH Access** –
    - Disable password authentication (use SSH keys).
    - Change the default SSH port from 22.
    - Enable fail2ban to block brute-force attempts.
4. **Restrict RCON and Remote Console Access** – Either disable RCON or restrict it to localhost and use a strong password.
5. **Isolate Your Server in a Docker Container or VM** – Prevent attackers from affecting your whole system.
6. **Use SELinux or AppArmor** – Adds an extra layer of security by restricting what the server process can access.
7. **Monitor System Logs** – Regularly check logs like `/var/log/auth.log` and `/var/log/syslog` for unauthorized access attempts.
8. **Set Up Automated Backups** – Use a cron job or backup service to regularly back up your world files and configs.
9. **Implement DDoS Protection** – Use a service like Cloudflare Spectrum, TCPShield, or a hosting provider with built-in protection.
10. **Use a Reverse Proxy for Extra Security** – Running your server behind a reverse proxy (like BungeeCord or Waterfall) can help mitigate attacks.
11. **Harden Java Security** – Run Java with sandboxing options (`-Djava.security.manager`) to limit potential exploits.
12. **Limit Plugin Execution Rights** – Set file permissions so only necessary users can modify plugins and configs.

### ❌ **Things to Avoid**
1. **Never Open Unnecessary Ports** – Exposing extra services can be a security risk.
2. **Don’t Use Outdated Java Versions** – Older Java versions have known exploits.
3. **Don’t Store Passwords in Plain Text** – Use environment variables or secured config files.
4. **Avoid Using Public Wi-Fi to Manage Your Server** – If you must, use a VPN.
5. **Don’t Run Multiple Services on the Same Machine Without Isolation** – Keep your Minecraft server separate from critical applications.