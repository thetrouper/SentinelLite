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
import org.bukkit.event.block.BlockPlaceEvent;

public class CommandBlockPlace extends AbstractViolation {

    @EventHandler
    public void listen(BlockPlaceEvent e) {
        //ServerUtils.verbose("CommandBlockPlace: Detected block place");
        if (!SentinelLite.violationConfig.commandBlockPlace.enabled) return;
        //ServerUtils.verbose("CommandBlockPlace: Enabled");
        Player p = e.getPlayer();
        if (!p.isOp()) return;
        //ServerUtils.verbose("CommandBlockPlace: Player is operator");
        Block b = e.getBlockPlaced();
        if (!(b.getType().equals(Material.COMMAND_BLOCK) ||
                b.getType().equals(Material.REPEATING_COMMAND_BLOCK) ||
                b.getType().equals(Material.CHAIN_COMMAND_BLOCK))) return;
        ServerUtils.verbose("CommandBlockPlace: Block is a command block");
        CommandBlock cb = (CommandBlock) b.getState();
        if (PlayerUtils.isTrusted(p)) return;
        ServerUtils.verbose("CommandBlockPlace: Not trusted, performing action");


        ActionConfiguration.Builder config = new ActionConfiguration.Builder()
                .setEvent(e)
                .setPlayer(p)
                .deop(SentinelLite.violationConfig.commandBlockPlace.deop)
                .cancel(true)
                .setEvent(e)
                .punish(true)
                .setPunishmentCommands(SentinelLite.violationConfig.commandBlockPlace.punishmentCommands)
                .logToDiscord(SentinelLite.violationConfig.commandBlockPlace.logToDiscord);

        runActions(
                SentinelLite.lang.violations.protections.rootName.rootNameFormatPlayer.formatted(p.getName(), SentinelLite.lang.violations.protections.rootName.place, SentinelLite.lang.violations.protections.rootName.commandBlock),
                SentinelLite.lang.violations.protections.rootName.rootNameFormatPlayer.formatted(p.getName(), SentinelLite.lang.violations.protections.rootName.place, SentinelLite.lang.violations.protections.rootName.commandBlock),
                generateCommandBlockInfo(cb),
                config
        );
    }
}
