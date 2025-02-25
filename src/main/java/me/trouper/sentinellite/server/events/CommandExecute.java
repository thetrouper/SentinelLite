package me.trouper.sentinellite.server.events;

import me.trouper.sentinellite.SentinelLite;
import me.trouper.sentinellite.server.functions.helpers.AbstractViolation;
import me.trouper.sentinellite.server.functions.helpers.ActionConfiguration;
import me.trouper.sentinellite.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.HashSet;
import java.util.Set;

public class CommandExecute extends AbstractViolation {

    @EventHandler
    private void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if (PlayerUtils.isTrusted(p)) return;
        String label = e.getMessage().substring(1).split(" ")[0];
        String args = e.getMessage();

        Set<String> status = getCommandStatus(label);

        if (status.contains("SPECIFIC") && SentinelLite.violationConfig.commandExecute.specific.enabled) {
            e.setCancelled(true);
            ActionConfiguration.Builder config = new ActionConfiguration.Builder()
                    .setEvent(e)
                    .setPlayer(p)
                    .cancel(true)
                    .punish(SentinelLite.violationConfig.commandExecute.specific.punish)
                    .setPunishmentCommands(SentinelLite.violationConfig.commandExecute.specific.punishmentCommands)
                    .logToDiscord(SentinelLite.violationConfig.commandExecute.specific.logToDiscord);

            runActions(
                    SentinelLite.lang.violations.protections.rootName.rootNameFormatPlayer.formatted(p.getName(), SentinelLite.lang.violations.protections.rootName.run, SentinelLite.lang.violations.protections.rootName.specificCommand),
                    SentinelLite.lang.violations.protections.rootName.rootNameFormatPlayer.formatted(p.getName(), SentinelLite.lang.violations.protections.rootName.run, SentinelLite.lang.violations.protections.rootName.specificCommand),
                    generateCommandInfo(args, p),
                    config
            );
            return;
        }

        if (status.contains("DANGEROUS") && SentinelLite.violationConfig.commandExecute.dangerous.enabled) {
            e.setCancelled(true);
            ActionConfiguration.Builder config = new ActionConfiguration.Builder()
                    .setEvent(e)
                    .setPlayer(p)
                    .deop(SentinelLite.violationConfig.commandExecute.dangerous.deop)
                    .cancel(true)
                    .punish(SentinelLite.violationConfig.commandExecute.dangerous.punish)
                    .setPunishmentCommands(SentinelLite.violationConfig.commandExecute.dangerous.punishmentCommands)
                    .logToDiscord(SentinelLite.violationConfig.commandExecute.dangerous.logToDiscord);

            runActions(
                    SentinelLite.lang.violations.protections.rootName.rootNameFormatPlayer.formatted(p.getName(), SentinelLite.lang.violations.protections.rootName.run, SentinelLite.lang.violations.protections.rootName.dangerousCommand),
                    SentinelLite.lang.violations.protections.rootName.rootNameFormatPlayer.formatted(p.getName(), SentinelLite.lang.violations.protections.rootName.run, SentinelLite.lang.violations.protections.rootName.dangerousCommand),
                    generateCommandInfo(args, p),
                    config
            );
            return;
        }

        if (status.contains("LOGGED") && SentinelLite.violationConfig.commandExecute.logged.enabled) {
            ActionConfiguration.Builder config = new ActionConfiguration.Builder()
                    .setPlayer(p)
                    .logToDiscord(SentinelLite.violationConfig.commandExecute.logged.logToDiscord);

            runActions(
                    SentinelLite.lang.violations.protections.rootName.rootNameFormatPlayer.formatted(p.getName(), SentinelLite.lang.violations.protections.rootName.run, SentinelLite.lang.violations.protections.rootName.loggedCommand),
                    SentinelLite.lang.violations.protections.rootName.rootNameFormatPlayer.formatted(p.getName(), SentinelLite.lang.violations.protections.rootName.run, SentinelLite.lang.violations.protections.rootName.loggedCommand),
                    generateCommandInfo(args, p),
                    config
            );
            return;
        }
    }

    public static Set<String> getCommandStatus(String label) {
        Set<String> commandTypes = new HashSet<>();

        if (label.startsWith("/")) {
            label = label.substring(1);
        }

        if (label.contains(":")) {
            commandTypes.add("SPECIFIC");
        }

        for (String loggedCommand : SentinelLite.violationConfig.commandExecute.logged.commands) {
            if (loggedCommand.equals(label)) commandTypes.add("LOGGED");
        }

        for (String dangerousCommand : SentinelLite.violationConfig.commandExecute.dangerous.commands) {
            if (dangerousCommand.equals(label)) commandTypes.add("DANGEROUS");
        }

        return commandTypes;
    }
}