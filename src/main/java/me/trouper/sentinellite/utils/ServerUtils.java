package me.trouper.sentinellite.utils;

import me.trouper.sentinellite.SentinelLite;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ServerUtils {
    public static void sendCommand(String command) {
        ServerUtils.verbose("Getting scheduler");
        Bukkit.getScheduler().scheduleSyncDelayedTask(SentinelLite.getInstance(), () -> {
            try {
                ServerUtils.verbose("Attempting to run command...");
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
            } catch (Exception e) {
                e.printStackTrace();
            }
        },1);
    }

    public static void verbose(String message) {
        if (SentinelLite.mainConfig.debugMode) {
            String log = "[Sentinel] [DEBUG]: " + message;
            SentinelLite.log.info(log);
            for (Player trustedPlayer : Bukkit.getOnlinePlayers()) {
                if (PlayerUtils.isTrusted(trustedPlayer)) {
                    trustedPlayer.sendMessage("§d§lSentinel §7[§bDEBUG§7] §8» §7" + message);
                }
            }
        }
    }

    public static List<Player> getPlayers() {
        return new ArrayList<>(Bukkit.getOnlinePlayers());
    }

    public static void forEachPlayer(Consumer<Player> consumer) {
        getPlayers().forEach(consumer);
    }

}
