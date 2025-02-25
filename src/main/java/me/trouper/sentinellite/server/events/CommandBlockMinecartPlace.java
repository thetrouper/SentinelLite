package me.trouper.sentinellite.server.events;

import me.trouper.sentinellite.SentinelLite;
import me.trouper.sentinellite.server.functions.helpers.AbstractViolation;
import me.trouper.sentinellite.server.functions.helpers.ActionConfiguration;
import me.trouper.sentinellite.utils.PlayerUtils;
import me.trouper.sentinellite.utils.ServerUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class CommandBlockMinecartPlace extends AbstractViolation {

    @EventHandler
    private void onCMDMinecartPlace(PlayerInteractEvent e) {
        //ServerUtils.verbose("MinecartCommandPlace: Detected interaction");
        if (!SentinelLite.violationConfig.commandBlockMinecartPlace.enabled) return;
        //ServerUtils.verbose("MinecartCommandPlace: Check is enabled");
        Player p = e.getPlayer();
        if (!p.isOp()) return;
        //ServerUtils.verbose("MinecartCommandPlace: Player is op");
        if (e.getItem() == null) return;
        ServerUtils.verbose("MinecartCommandPlace: Item isn't null");
        if (e.getClickedBlock() == null) return;
        ServerUtils.verbose("MinecartCommandPlace: Clicked block isn't null");
        if (!e.getItem().getType().equals(Material.COMMAND_BLOCK_MINECART)) return;
        ServerUtils.verbose("MinecartCommandPlace: Item is a minecart command");
        if (!(e.getClickedBlock().getType() == Material.RAIL || e.getClickedBlock().getType() == Material.POWERED_RAIL || e.getClickedBlock().getType() == Material.ACTIVATOR_RAIL || e.getClickedBlock().getType() == Material.DETECTOR_RAIL)) return;
        ServerUtils.verbose("MinecartCommandPlace: Clicked block is a rail");
        if (PlayerUtils.isTrusted(p)) return;
        ServerUtils.verbose("MinecartCommandPlace: Not trusted, performing action");

        ActionConfiguration.Builder config = new ActionConfiguration.Builder()
                .setEvent(e)
                .setPlayer(p)
                .cancel(true)
                .punish(SentinelLite.violationConfig.commandBlockMinecartPlace.punish)
                .deop(SentinelLite.violationConfig.commandBlockMinecartPlace.deop)
                .setPunishmentCommands(SentinelLite.violationConfig.commandBlockMinecartPlace.punishmentCommands)
                .logToDiscord(SentinelLite.violationConfig.commandBlockMinecartPlace.logToDiscord);

        // Remove the command block minecart from the player's inventory
        p.getInventory().remove(Material.COMMAND_BLOCK_MINECART);

        runActions(
                SentinelLite.lang.violations.protections.rootName.rootNameFormatPlayer.formatted(p.getName(), SentinelLite.lang.violations.protections.rootName.place, SentinelLite.lang.violations.protections.rootName.minecartCommandBlock),
                SentinelLite.lang.violations.protections.rootName.rootNameFormatPlayer.formatted(p.getName(), SentinelLite.lang.violations.protections.rootName.place, SentinelLite.lang.violations.protections.rootName.minecartCommandBlock),
                generateBlockInfo(e.getClickedBlock()),
                config
        );
    }
}