package me.trouper.sentinellite.server.events;

import me.trouper.sentinellite.SentinelLite;
import me.trouper.sentinellite.server.functions.helpers.AbstractViolation;
import me.trouper.sentinellite.server.functions.helpers.ActionConfiguration;
import me.trouper.sentinellite.utils.PlayerUtils;
import me.trouper.sentinellite.utils.ServerUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class CommandBlockMinecartUse extends AbstractViolation {

    @EventHandler
    private void onCMDBlockMinecartUse(PlayerInteractEntityEvent e) {
        //ServerUtils.verbose("MinecartCommandUse: Detected Interaction with entity");
        if (!SentinelLite.violationConfig.commandBlockMinecartUse.enabled) return;
        //ServerUtils.verbose("MinecartCommandUse: Enabled");
        Player p = e.getPlayer();
        if (!p.isOp()) return;
        ServerUtils.verbose("MinecartCommandUse: Player op");
        if (e.getRightClicked().getType() != EntityType.COMMAND_BLOCK_MINECART) return;
        ServerUtils.verbose("MinecartCommandUse: Entity is minecart command");
        if (PlayerUtils.isTrusted(p)) return;
        ServerUtils.verbose("MinecartCommandUse: Not trusted, performing action");

        ActionConfiguration.Builder config = new ActionConfiguration.Builder()
                .setEvent(e)
                .setPlayer(p)
                .cancel(true)
                .punish(SentinelLite.violationConfig.commandBlockMinecartUse.punish)
                .deop(SentinelLite.violationConfig.commandBlockMinecartUse.deop)
                .setPunishmentCommands(SentinelLite.violationConfig.commandBlockMinecartUse.punishmentCommands)
                .logToDiscord(SentinelLite.violationConfig.commandBlockMinecartUse.logToDiscord);

        runActions(
                SentinelLite.lang.violations.protections.rootName.rootNameFormatPlayer.formatted(p.getName(), SentinelLite.lang.violations.protections.rootName.use, SentinelLite.lang.violations.protections.rootName.minecartCommandBlock),
                SentinelLite.lang.violations.protections.rootName.rootNameFormatPlayer.formatted(p.getName(), SentinelLite.lang.violations.protections.rootName.use, SentinelLite.lang.violations.protections.rootName.minecartCommandBlock),
                generateMinecartInfo(e.getRightClicked()),
                config
        );
    }
}