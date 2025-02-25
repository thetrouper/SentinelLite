package me.trouper.sentinellite.utils;

import me.trouper.sentinellite.SentinelLite;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class PlayerUtils {
    public static boolean isTrusted(Player player) {
        return SentinelLite.mainConfig.plugin.trustedPlayers.contains(player.getUniqueId().toString());
    }

    public static boolean isTrusted(String uuid) {
        return SentinelLite.mainConfig.plugin.trustedPlayers.contains(uuid);
    }

    public static boolean isTrusted(CommandSender sender) {
        return (sender instanceof Player p && isTrusted(p)) || sender instanceof ConsoleCommandSender;
    }

    public static boolean playerCheck(CommandSender sender) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage(Text.prefix(SentinelLite.lang.permissions.playersOnly));
            return false;
        }
        return true;
    }

    public static boolean checkPermission(CommandSender sender, String permission) {
        if (sender instanceof ConsoleCommandSender || (sender instanceof Player p && p.hasPermission(permission))) return true;
        sender.sendMessage(SentinelLite.lang.permissions.noPermission);
        return false;
    }
}
