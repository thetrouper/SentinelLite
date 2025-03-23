# **Installation**
The installation for both the lite version and the full version are similar.

1. Download the plugin from its release page
2. Upload the plugin to your server in its `./plugins/` folder
3. Use /reload or reboot your server to load the plugin. **Do not use PlugMan or PluginManager**
4. When loading for the first time, Sentinel should throw 16 exceptions. This is expected, it is how the config files are created. Sending reports of config generation stack traces will result in you being ignored.
5. **If you are using Sentinel Anti-Nuke** (Full Version)
    - Join the [Support Discord](https://trouper.me/sentinel)
    - Start a ticket under the `✅ Verify Purchase` category. It will ask you for your BuiltByBit Username and Server ID, so prepare those.
    - You can find the ID in your console. Sentinel prints ASCII art so it should be easy to find.
    ```]====---- Requesting Authentication ----====[```<br>
    ```- License Key: null```<br>
    ```- Server ID: ad3fafe2d4e4728df19c1cfd138b7d8482d9bb67f1bdc89a8ce4ec8b13bb8943```<br>
    - Presuming you inputted your BBB username and Initial Server ID correctly, you will receive a working license key.
    - Add the **licnese key** to the `license` property of the `plugin` section in the `main-config.json` file of the `./plugins/SentinelAntiNuke` folder.
    - Reload the plugin with /sentinel reload from console, or reboot your server.
6. Config files can be found in the `./plugins/SentinelAntiNuke` or `./plugins/SentinelLite` folder.
    - View the [configuration](config/configuration.md) section next.