package me.trouper.sentinellite.server.events;

import io.github.itzispyder.pdk.events.CustomListener;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.trouper.sentinellite.SentinelLite;
import me.trouper.sentinellite.server.functions.chatfilter.profanity.ProfanityFilter;
import me.trouper.sentinellite.utils.PlayerUtils;
import me.trouper.sentinellite.utils.ServerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.function.Consumer;

public class ChatEvent implements CustomListener {

    @EventHandler
    private void onChat(AsyncChatEvent e) {
        ServerUtils.verbose("Chat event sanity check:\n Canceled %s".formatted(e.isCancelled()));
        handleEvent(e);
    }

    public void handleEvent(AsyncChatEvent e) {
        if (e.isCancelled()) return;
        if (PlayerUtils.isTrusted(e.getPlayer().getUniqueId().toString())) return;

        Player p = e.getPlayer();

        ServerUtils.verbose("Chat event start after trust check:\n Canceled %s".formatted(e.isCancelled()));

        handle(p,
                "sentinel.chatfilter.swear.bypass",
                SentinelLite.mainConfig.chat.profanityFilter.enabled,
                "swear",
                e,
                ProfanityFilter::handleProfanityFilter);

        ServerUtils.verbose("Chat event ending after swear:\n Canceled %s".formatted(e.isCancelled()));
    }

    private static void handle(Player p, String permission, boolean isEnabled, String eventType, AsyncChatEvent e, Consumer<AsyncChatEvent> handler) {
        ServerUtils.verbose("Handeling a chat filter:\n Canceled %s\nType: %s".formatted(e.isCancelled(),eventType));
        if (e.isCancelled()) return;
        if (p.hasPermission(permission)) return;
        ServerUtils.verbose("ChatEvent: Permission bypass failed, checking for " + eventType);
        if (!isEnabled) return;
        ServerUtils.verbose("ChatEvent: " + eventType + " check enabled, continuing!");
        handler.accept(e);
    }
}
