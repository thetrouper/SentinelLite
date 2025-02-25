package me.trouper.sentinellite.server.functions.helpers;

import io.github.itzispyder.pdk.utils.ServerUtils;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.trouper.sentinellite.SentinelLite;
import me.trouper.sentinellite.server.commands.SentinelCommand;
import me.trouper.sentinellite.server.events.ChatEvent;
import net.kyori.adventure.chat.SignedMessage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;

import java.util.*;

public class Message {
    public static final Map<UUID,UUID> replyMap = new HashMap<>();
    public static void messagePlayer(Player sender, Player receiver, String message) {
        AsyncChatEvent checkEvent = new AsyncChatEvent(true,sender, new HashSet<>(Arrays.asList(receiver, sender)), ChatRenderer.defaultRenderer(),Component.text(message),Component.text(message), SignedMessage.system(message,Component.text(message)));
        if (checkEvent.isCancelled()) return;
        new ChatEvent().handleEvent(checkEvent);
        if (checkEvent.isCancelled()) return;

        sender.sendMessage(SentinelLite.lang.playerInteraction.messageSent.formatted(receiver.getName(),message));
        receiver.sendMessage(SentinelLite.lang.playerInteraction.messageReceived.formatted(sender.getName(),message));
        replyMap.put(receiver.getUniqueId(),sender.getUniqueId());
        sendSpy(sender,receiver,message);
    }

    public static void sendSpy(Player sender, Player receiver, String message) {
        ServerUtils.forEachPlayer(player -> {

            if (SentinelCommand.spyMap.getOrDefault(player.getUniqueId(),false)) {
                TextComponent notification = Component
                        .text(SentinelLite.lang.socialSpy.spyMessage.formatted(sender.getName(),receiver.getName()))
                        .hoverEvent(Component.text(SentinelLite.lang.socialSpy.spyMessageHover.formatted(sender.getName(),receiver.getName(),message)));
                player.sendMessage(notification);
            }
        });
    }
}
