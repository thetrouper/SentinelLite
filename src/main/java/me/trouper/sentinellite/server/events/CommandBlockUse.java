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
import org.bukkit.event.player.PlayerInteractEvent;

public class CommandBlockUse extends AbstractViolation {

    @EventHandler
    private void onCMDBlockUse(PlayerInteractEvent e) {
        //ServerUtils.verbose("CommandBlockUse: Detected Interaction");
        if (!SentinelLite.violationConfig.commandBlockUse.enabled) return;
        //ServerUtils.verbose("CommandBlockUse: Enabled");
        Player p = e.getPlayer();
        if (!p.isOp()) return;
        //ServerUtils.verbose("CommandBlockUse: Player is op");
        if (e.getClickedBlock() == null) return;
        //ServerUtils.verbose("CommandBlockUse: Block isn't null");
        Block b = e.getClickedBlock();
        if (!(b.getType() == Material.COMMAND_BLOCK || b.getType() == Material.REPEATING_COMMAND_BLOCK || b.getType() == Material.CHAIN_COMMAND_BLOCK)) return;
        CommandBlock cb = (CommandBlock) b.getState();
        ServerUtils.verbose("CommandBlockUse: Block is a command block");
        if (PlayerUtils.isTrusted(p)) return;
        ServerUtils.verbose("CommandBlockUse: Not trusted, performing action");

        ActionConfiguration.Builder config = new ActionConfiguration.Builder()
                .setEvent(e)
                .setPlayer(p)
                .deop(SentinelLite.violationConfig.commandBlockUse.deop)
                .cancel(true)
                .punish(SentinelLite.violationConfig.commandBlockUse.punish)
                .setPunishmentCommands(SentinelLite.violationConfig.commandBlockUse.punishmentCommands)
                .logToDiscord(SentinelLite.violationConfig.commandBlockUse.logToDiscord);

        runActions(
                SentinelLite.lang.violations.protections.rootName.rootNameFormatPlayer.formatted(p.getName(), SentinelLite.lang.violations.protections.rootName.use, SentinelLite.lang.violations.protections.rootName.commandBlock),
                SentinelLite.lang.violations.protections.rootName.rootNameFormatPlayer.formatted(p.getName(), SentinelLite.lang.violations.protections.rootName.use, SentinelLite.lang.violations.protections.rootName.commandBlock),
                generateCommandBlockInfo(cb),
                config
        );
    }
}