package me.trouper.sentinellite.server.events;

import me.trouper.sentinellite.SentinelLite;
import me.trouper.sentinellite.server.functions.helpers.AbstractViolation;
import me.trouper.sentinellite.server.functions.helpers.ActionConfiguration;
import me.trouper.sentinellite.utils.PlayerUtils;
import me.trouper.sentinellite.utils.ServerUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class CommandBlockEdit extends AbstractViolation {

    @EventHandler
    private void onCMDBlockChange(EntityChangeBlockEvent e) {
        //ServerUtils.verbose("CommandBlockChange: Detected the event");
        if (!SentinelLite.violationConfig.commandBlockEdit.enabled) return;
        //ServerUtils.verbose("CommandBlockChange: Enabled");
        if (!(e.getEntity() instanceof Player p)) return;
        //ServerUtils.verbose("CommandBlockChange: Changer is a player");
        Block b = e.getBlock();
        if (!(b.getType() == Material.COMMAND_BLOCK || b.getType() == Material.REPEATING_COMMAND_BLOCK || b.getType() == Material.CHAIN_COMMAND_BLOCK))
            return;
        ServerUtils.verbose("CommandBlockChange: Block is a command block");
        CommandBlock cb = (CommandBlock) b.getState();
        if (PlayerUtils.isTrusted(p)) return;

        ServerUtils.verbose("CommandBlockChange: Not trusted, performing action");

        ActionConfiguration.Builder config = new ActionConfiguration.Builder()
                .setEvent(e)
                .setPlayer(p)
                .deop(SentinelLite.violationConfig.commandBlockEdit.deop)
                .cancel(true)
                .punish(SentinelLite.violationConfig.commandBlockEdit.punish)
                .setPunishmentCommands(SentinelLite.violationConfig.commandBlockEdit.punishmentCommands)
                .logToDiscord(SentinelLite.violationConfig.commandBlockEdit.logToDiscord);

        runActions(
                SentinelLite.lang.violations.protections.rootName.rootNameFormatPlayer.formatted(p.getName(), SentinelLite.lang.violations.protections.rootName.edit, SentinelLite.lang.violations.protections.rootName.commandBlock),
                SentinelLite.lang.violations.protections.rootName.rootNameFormatPlayer.formatted(p.getName(), SentinelLite.lang.violations.protections.rootName.edit, SentinelLite.lang.violations.protections.rootName.commandBlock),
                generateCommandBlockInfo(cb),
                config
        );
    }
}
