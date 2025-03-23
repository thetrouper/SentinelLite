# **Backdoor Detection**
Only **YOU** can prevent backdoors. Even though Sentinel's Anti-Backdoor raises the bar for attackers,
it is always good to follow these practices.

By following [these guidelines](backdoorprevention.md), you can significantly reduce the risk of getting backdoored.

### **If you wish to go the extra mile...**
...Enable Sentinel Anti Nuke's Backdoor Detection.

Its configuration is in this section.
```json
"backdoorDetection": {
    "enabled": false,
    "setupMode": true,
    "keepSetupMode": true
}
```
To enable it, simply change the "false" in `"enabled": false,` to "true". 

### **After enabling Backdoor Detection:**
You will need to reboot your server to continue its installation.
When sentinel boots, the module will generate a custom server jar that includes plugin file verification on startup.
The custom jar will be named 'YourServerJarName-patched.jar'. Simply delete your server's original jar, and rename the patched jar.
