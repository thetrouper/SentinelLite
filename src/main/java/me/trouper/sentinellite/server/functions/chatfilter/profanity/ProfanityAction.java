package me.trouper.sentinellite.server.functions.chatfilter.profanity;

import me.trouper.sentinellite.SentinelLite;
import me.trouper.sentinellite.server.functions.chatfilter.AbstractActionHandler;
import me.trouper.sentinellite.utils.ServerUtils;
import me.trouper.sentinellite.utils.Text;
import me.trouper.sentinellite.utils.trees.HoverFormatter;
import me.trouper.sentinellite.utils.trees.Node;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;

public class ProfanityAction extends AbstractActionHandler<ProfanityResponse> {

    @Override
    public void punish(ProfanityResponse response) {
        if (response.getSeverity().equals(Severity.SLUR)) {
            for (String slurCommand : SentinelLite.mainConfig.chat.profanityFilter.strictPunishCommands) {
                ServerUtils.sendCommand(slurCommand.replaceAll("%player%", response.getPlayer().getName()));
            }
        }
        for (String swearCommand : SentinelLite.mainConfig.chat.profanityFilter.profanityPunishCommands) {
            ServerUtils.sendCommand(swearCommand.replaceAll("%player%", response.getPlayer().getName()));
        }
    }

    @Override
    public void staffWarning(ProfanityResponse response, Node tree) {
        String messageText = Text.prefix("&b&n%s&r &7%s &8(&4%s&7/&c%s&8)".formatted(
                response.getPlayer().getName(),
                response.isPunished() ? SentinelLite.lang.violations.chat.profanity.autoPunishNotification : SentinelLite.lang.violations.chat.profanity.preventNotification,
                ProfanityFilter.scoreMap.getOrDefault(response.getPlayer().getUniqueId(), 0),
                SentinelLite.mainConfig.chat.profanityFilter.punishScore
        ));
        String hoverText = HoverFormatter.format(tree);

        ServerUtils.forEachPlayer(player -> {
            if (player.hasPermission("sentinel.chatfilter.profanity.view")) player.sendMessage(Component.text(messageText).hoverEvent(Component.text(hoverText).asHoverEvent()));
        });
    }

    @Override
    public void playerWarning(ProfanityResponse response) {
        String message = Text.prefix(response.isPunished() ? SentinelLite.lang.violations.chat.profanity.autoPunishWarning : SentinelLite.lang.violations.chat.profanity.preventWarning);
        String hoverText = SentinelLite.lang.automatedActions.reportable;
        response.getPlayer().sendMessage(
                Component.text(message)
                .hoverEvent(Component.text(hoverText).asHoverEvent())
        );
    }

    @Override
    public Node buildTree(ProfanityResponse response)  {
        Node root = new Node("Sentinel");
        root.addTextLine(SentinelLite.lang.violations.chat.profanity.treeTitle);

        Node playerInfo = new Node(SentinelLite.lang.violations.protections.infoNode.playerInfo.formatted(response.getPlayer().getName()));
        playerInfo.addKeyValue(SentinelLite.lang.violations.protections.infoNode.uuid, response.getPlayer().getUniqueId().toString());
        playerInfo.addKeyValue(SentinelLite.lang.violations.chat.profanity.score, "%s/%s".formatted(ProfanityFilter.scoreMap.getOrDefault(response.getPlayer().getUniqueId(),0), SentinelLite.mainConfig.chat.profanityFilter.punishScore));
        root.addChild(playerInfo);

        Node reportInfo = new Node(SentinelLite.lang.violations.chat.profanity.reportInfoTitle);
        reportInfo.addField(SentinelLite.lang.violations.chat.originalMessage, response.getOriginalMessage());
        reportInfo.addField(SentinelLite.lang.violations.chat.profanity.processedMessage, response.getProcessedMessage());
        reportInfo.addKeyValue(SentinelLite.lang.violations.chat.profanity.severity, response.getSeverity().toString());
        root.addChild(reportInfo);

        Node actions = new Node(SentinelLite.lang.violations.protections.actionNode.actionNodeTitle);
        actions.addTextLine(SentinelLite.lang.violations.chat.denyMessage);
        if (response.isPunished()) actions.addTextLine(SentinelLite.lang.violations.protections.actionNode.punishmentCommandsExecuted);
        root.addChild(actions);

        return root;
    }

    @Override
    protected boolean shouldWarnPlayer(ProfanityResponse response) {
        return !SentinelLite.mainConfig.chat.profanityFilter.silent;
    }
}
