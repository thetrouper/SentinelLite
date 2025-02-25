package me.trouper.sentinellite.server.functions.chatfilter;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;

public interface FilterResponse {
    AsyncChatEvent getEvent();
    Player getPlayer();
    boolean isBlocked();
    boolean isPunished();
}