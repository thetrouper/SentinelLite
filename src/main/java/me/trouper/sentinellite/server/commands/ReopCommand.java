package me.trouper.sentinellite.server.commands;

import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import me.trouper.sentinellite.SentinelLite;
import me.trouper.sentinellite.utils.PlayerUtils;
import me.trouper.sentinellite.utils.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandRegistry(value = "reop")
public class ReopCommand implements CustomCommand {
    @Override
    public void dispatchCommand(CommandSender sender, Command command, String s, Args args) {
        Player p = (Player) sender;
        if (PlayerUtils.isTrusted(p) && SentinelLite.mainConfig.plugin.reopCommand) {
            if (!p.isOp()) {
                p.sendMessage(Text.prefix(SentinelLite.lang.permissions.elevatingPerms));
                SentinelLite.log.info(SentinelLite.lang.permissions.logElevatingPerms.formatted(p.getName()));
                p.setOp(true);
            } else {
                p.sendMessage(Text.prefix(SentinelLite.lang.permissions.alreadyOp));
                SentinelLite.log.info(SentinelLite.lang.permissions.logAlreadyOp.formatted(p.getName()));
                p.setOp(true);
            }
        } else {
            p.sendMessage(Text.prefix(SentinelLite.lang.permissions.noTrust));
        }

    }

    @Override
    public void dispatchCompletions(CommandSender commandSender, Command command, String s, CompletionBuilder completionBuilder) {

    }
}
