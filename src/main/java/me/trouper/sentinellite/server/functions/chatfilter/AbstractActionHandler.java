package me.trouper.sentinellite.server.functions.chatfilter;

import io.github.itzispyder.pdk.utils.discord.DiscordEmbed;
import me.trouper.sentinellite.SentinelLite;
import me.trouper.sentinellite.server.functions.helpers.FilterHelpers;
import me.trouper.sentinellite.utils.trees.ConsoleFormatter;
import me.trouper.sentinellite.utils.trees.EmbedFormatter;
import me.trouper.sentinellite.utils.trees.Node;

public abstract class AbstractActionHandler<T extends FilterResponse> {

    public void run(T response) {
        Node tree = buildTree(response);

        if (response.isBlocked()) {
            FilterHelpers.restrictMessage(response.getEvent(),!shouldWarnPlayer(response));
        }
        if (response.isPunished()) {
            punish(response);
            discordNotification(tree);
        }
        if (shouldWarnPlayer(response)) {
            playerWarning(response);
        }

        staffWarning(response, tree);
        consoleLog(tree);
    }

    protected abstract void punish(T response);
    protected abstract void staffWarning(T response, Node tree);
    protected abstract void playerWarning(T response);
    protected abstract Node buildTree(T response);
    protected abstract boolean shouldWarnPlayer(T response);

    protected void consoleLog(Node tree) {
        SentinelLite.log.info(ConsoleFormatter.format(tree));
    }

    protected void discordNotification(Node tree) {
        DiscordEmbed embed = EmbedFormatter.format(tree);
        EmbedFormatter.sendEmbed(embed);
    }
}
