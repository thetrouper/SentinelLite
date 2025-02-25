package me.trouper.sentinellite.server.functions.helpers;

import io.github.itzispyder.pdk.events.CustomListener;
import me.trouper.sentinellite.SentinelLite;
import me.trouper.sentinellite.utils.FileUtils;
import me.trouper.sentinellite.utils.PlayerUtils;
import me.trouper.sentinellite.utils.ServerUtils;
import me.trouper.sentinellite.utils.Text;
import me.trouper.sentinellite.utils.trees.ConsoleFormatter;
import me.trouper.sentinellite.utils.trees.EmbedFormatter;
import me.trouper.sentinellite.utils.trees.HoverFormatter;
import me.trouper.sentinellite.utils.trees.Node;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractViolation implements CustomListener {

    public void runActions(String rootName, String rootNamePlayer, Node violationInfo, ActionConfiguration.Builder configuration) {
        ActionConfiguration config = configuration.build();

        Node root = new Node("Sentinel");
        root.addTextLine(rootName);

        if (config.getPlayer() != null) root.addChild(generatePlayerInfo(config.getPlayer()));

        root.addChild(violationInfo);

        root.addChild(configuration.getActionNode());

        notifyTrusted(root,(rootNamePlayer == null || rootNamePlayer.isBlank()) ? rootName : rootNamePlayer);
        if (configuration.isLoggedToDiscord()) EmbedFormatter.sendEmbed(EmbedFormatter.format(root));
        SentinelLite.log.info(ConsoleFormatter.format(root));
    }

    public void notifyTrusted(Node root, String rootNamePlayer) {
        ServerUtils.forEachPlayer(trusted -> {
            if (PlayerUtils.isTrusted(trusted)) {
                trusted.sendMessage(Component.text(Text.prefix(rootNamePlayer)).hoverEvent(Component.text(HoverFormatter.format(root)).asHoverEvent()));
            }
        });
    }

    public Node generatePlayerInfo(Player p) {
        Node playerInfo = new Node(SentinelLite.lang.violations.protections.infoNode.playerInfo);
        playerInfo.addKeyValue(SentinelLite.lang.violations.protections.infoNode.name, p.getName());
        playerInfo.addKeyValue(SentinelLite.lang.violations.protections.infoNode.uuid, p.getUniqueId().toString());
        playerInfo.addKeyValue(SentinelLite.lang.violations.protections.infoNode.operator, p.isOp() ? SentinelLite.lang.generic.yes : SentinelLite.lang.generic.no);
        playerInfo.addField(SentinelLite.lang.violations.protections.infoNode.locationField, SentinelLite.lang.violations.protections.infoNode.locationFormat.formatted(Math.round(p.getX()), Math.round(p.getY()), Math.round(p.getZ())));

        return playerInfo;
    }

    public static Node generateBlockInfo(Block block) {
        Node blockInfo = new Node(SentinelLite.lang.violations.protections.infoNode.blockInfo);
        blockInfo.addTextLine(Text.cleanName(block.getType().toString()));
        blockInfo.addKeyValue(SentinelLite.lang.violations.protections.infoNode.worldField,block.getWorld().getName());
        blockInfo.addField(SentinelLite.lang.violations.protections.infoNode.blockLocationField, SentinelLite.lang.violations.protections.infoNode.locationFormat.formatted(block.getX(), block.getY(), block.getZ()));

        return blockInfo;
    }

    public Node generateCommandBlockInfo(CommandBlock commandBlock) {
        Node commandBlockInfo = new Node(SentinelLite.lang.violations.protections.infoNode.blockInfo);
        commandBlockInfo.addTextLine(Text.cleanName(commandBlock.getType().toString()));
        commandBlockInfo.addKeyValue(SentinelLite.lang.violations.protections.infoNode.worldField,commandBlock.getWorld().getName());
        commandBlockInfo.addField(SentinelLite.lang.violations.protections.infoNode.blockLocationField, SentinelLite.lang.violations.protections.infoNode.locationFormat.formatted(commandBlock.getX(), commandBlock.getY(), commandBlock.getZ()));

        String command = commandBlock.getCommand();
        if (command == null || command.isBlank()) {
            return commandBlockInfo;
        } else if (command.length() <= 128) {
            commandBlockInfo.addField(SentinelLite.lang.violations.protections.infoNode.commandField, command);
        } else {
            commandBlockInfo.addField(SentinelLite.lang.violations.protections.infoNode.commandTooLargeField, FileUtils.createCommandLog(command));
        }

        return commandBlockInfo;
    }

    public Node generateMinecartInfo(Entity entity) {
        Node minecartInfo = new Node(SentinelLite.lang.violations.protections.infoNode.minecartInfo);
        minecartInfo.addTextLine(Text.cleanName(entity.getType().toString()));
        minecartInfo.addKeyValue(SentinelLite.lang.violations.protections.infoNode.worldField,entity.getWorld().getName());
        minecartInfo.addField(SentinelLite.lang.violations.protections.infoNode.cartLocationField, SentinelLite.lang.violations.protections.infoNode.locationFormat.formatted(Math.round(entity.getX()), Math.round(entity.getY()), Math.round(entity.getZ())));

        return minecartInfo;
    }

    public Node generateItemInfo(ItemStack item) {
        Node itemInfo = new Node(SentinelLite.lang.violations.protections.infoNode.itemInfo);
        itemInfo.addTextLine(Text.cleanName(item.getType().toString()));
        itemInfo.addKeyValue(SentinelLite.lang.violations.protections.infoNode.hasMeta,item.hasItemMeta() ? SentinelLite.lang.generic.yes : SentinelLite.lang.generic.no);
        if (item.hasItemMeta()) {
            itemInfo.addKeyValue(SentinelLite.lang.violations.protections.infoNode.hasName,item.getItemMeta().hasCustomName() ? SentinelLite.lang.generic.yes : SentinelLite.lang.generic.no);
            itemInfo.addKeyValue(SentinelLite.lang.violations.protections.infoNode.hasLore,item.getItemMeta().hasLore() ? SentinelLite.lang.generic.yes : SentinelLite.lang.generic.no);
            itemInfo.addKeyValue(SentinelLite.lang.violations.protections.infoNode.hasAttributes,item.getItemMeta().hasAttributeModifiers() ? SentinelLite.lang.generic.yes : SentinelLite.lang.generic.no);
            itemInfo.addKeyValue(SentinelLite.lang.violations.protections.infoNode.hasEnchants,item.getItemMeta().hasEnchants() ? SentinelLite.lang.generic.yes : SentinelLite.lang.generic.no);
            itemInfo.addField(SentinelLite.lang.violations.protections.infoNode.nbtStored, FileUtils.createNBTLog(item));
        }

        return itemInfo;
    }

    public Node generateCommandInfo(String command, Player executor) {
        Node commandInfo = new Node(SentinelLite.lang.violations.protections.infoNode.commandInfo);
        String name = command.split(" ")[0].substring(1);
        ServerUtils.verbose("Command Name: " + name);
        Command executed = Bukkit.getServer().getCommandMap().getCommand(name);

        commandInfo.addKeyValue(SentinelLite.lang.violations.protections.infoNode.name,name);
        if (command.length() <= 128) {
            commandInfo.addField(SentinelLite.lang.violations.protections.infoNode.commandField, command);
        } else {
            commandInfo.addField(SentinelLite.lang.violations.protections.infoNode.commandTooLargeField, FileUtils.createCommandLog(command));
        }
        if (executed == null || executed.getPermission() == null) return commandInfo;
        commandInfo.addKeyValue(SentinelLite.lang.violations.protections.infoNode.permissionRequired,executed.getPermission());
        commandInfo.addKeyValue(SentinelLite.lang.violations.protections.infoNode.permissionSatisfied,executor.hasPermission(executed.getPermission()) ? SentinelLite.lang.generic.yes : SentinelLite.lang.generic.no);

        return commandInfo;
    }
}
