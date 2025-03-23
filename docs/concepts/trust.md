# **How "Trust" Works**
Sentinel implements its own unique permission type, higher than operators. It is called "trust".
Trust is an unconditional bypass to all of Sentinel's checks. **Only you**, (or people financially motivated or otherwise aligned with you), should have it.
Giving an __untrusted__ player __trust__ completely nullifies its meaning.
If people would just treat **"operator"** like this, there would be no need for this plugin.
## **What is a "trusted User"?**
A "trusted user" is a user whose UUID is listed in the main config.
You will need trust to run any administrative features in the plugin, such as the [Command Block Whitelist](../features/commandblockwhitelist.md), or the GUI Configuration.

## **How do I add a trusted user?**
1. Locate the `main-config.json` in the plugin's data folder. (`plugins/SentinelAntiNuke/` or `plugins/SentinelLite/`)
    - By default, in the plugin section you will see this: `"trustedPlayers": ["049460f7-21cb-42f5-8059-d42752bf406f"],` 
2. Replace [my UUID](https://namemc.com/profile/obvWolf.1?q=049460f7-21cb-42f5-8059-d42752bf406f) with your own, or of people you trust.
3. You can add more people to the list by adding a comma and another string
    - `"trustedPlayers": ["049460f7-21cb-42f5-8059-d42752bf406f", "488016ec-679a-489e-9f42-4eb0276761bb"]`

Be careful who's UUID you put here. They will bypass **EVERYTHING** in the plugin, are able to modify the configuration, and OP themselves.