package me.trouper.sentinellite.server.commands;

import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.trouper.sentinellite.SentinelLite;
import me.trouper.sentinellite.server.functions.chatfilter.profanity.ProfanityFilter;
import me.trouper.sentinellite.utils.PlayerUtils;
import me.trouper.sentinellite.utils.Text;
import me.trouper.sentinellite.utils.trees.ConsoleFormatter;
import me.trouper.sentinellite.utils.trees.EmbedFormatter;
import me.trouper.sentinellite.utils.trees.Node;
import net.kyori.adventure.chat.SignedMessage;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@CommandRegistry(value = "sentinel",permission = @Permission("sentinel.staff"),printStackTrace = true)
public class SentinelCommand implements CustomCommand {

    public static Map<UUID, Boolean> spyMap = new HashMap<>();

    @Override
    public void dispatchCommand(CommandSender sender, Command command, String s, Args args) {
        try {
            safety(sender,command,s,args);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(Text.prefix(SentinelLite.lang.plugin.invalidArgs));
        }
    }

    @Override
    public void dispatchCompletions(CommandSender commandSender, Command command, String s, CompletionBuilder b) {
        b.then(b.arg("socialspy"));
        b.then(b.arg("config"));
        b.then(b.arg("reload"));
        b.then(b.arg("debug").then(
                b.arg("lang","toggle","chat")));
    }


    private void safety(CommandSender sender, Command command, String label, Args args) {
        if (args.isEmpty()) {
            sender.sendMessage(Text.prefix("Usage: /sentinel <eload|debug|socialspy>"));
            return;
        }

        String subCommand = args.get(0).toString().toLowerCase();
        switch (subCommand) {
            case "reload" -> handleReload(sender);
            case "debug" -> handleDebugCommand(sender, args);
            case "socialspy" -> handleSocialSpy(sender);
            default -> sender.sendMessage(Text.prefix("Invalid sub-command. Usage: /sentinel <reload|debug|socialspy>"));
        }
    }


    private void handleReload(CommandSender sender) {
        if (sender instanceof Player p) {
            if (!PlayerUtils.checkPermission(sender, "sentinel.reload") || !PlayerUtils.isTrusted(p)) {
                p.sendMessage(Text.prefix(SentinelLite.lang.permissions.noTrust));
                return;
            }
        }
        SentinelLite.log.info("Sentinel is now reloading the config.");
        sender.sendMessage(Text.prefix(SentinelLite.lang.plugin.reloadingConfig));
        SentinelLite.getInstance().loadConfig();
    }

    private void handleDebugCommand(CommandSender sender, Args args) {
        if (!PlayerUtils.checkPermission(sender, "sentinel.debug")) return;
        if (args.getSize() < 2) {
            sender.sendMessage(Text.prefix("Usage: /sentinel debug <lang|toggle|chat>"));
            return;
        }
        String sub = args.get(1).toString().toLowerCase();
        switch (sub) {
            case "lang" -> sender.sendMessage(SentinelLite.lang.brokenLang);
            case "toggle" -> {
                SentinelLite.mainConfig.debugMode = !SentinelLite.mainConfig.debugMode;
                String message = SentinelLite.mainConfig.debugMode
                        ? SentinelLite.lang.debug.debugEnabled
                        : SentinelLite.lang.debug.debugDisabled;
                sender.sendMessage(Text.prefix(message));
                SentinelLite.mainConfig.save();
            }
            case "chat" -> {
                if (!PlayerUtils.playerCheck(sender)) return;
                if (args.getSize() < 3) {
                    sender.sendMessage(Text.prefix("Usage: /sentinel debug chat <message>"));
                    return;
                }
                Player p = (Player) sender;
                String messageText = args.getAll(2).toString();
                AsyncChatEvent message = new AsyncChatEvent(true,
                        p,
                        Set.of(p),
                        ChatRenderer.defaultRenderer(),
                        Component.text(messageText),
                        Component.text(messageText),
                        SignedMessage.system(messageText, Component.text(messageText))
                );
                ProfanityFilter.handleProfanityFilter(message);
                if (!message.isCancelled()) {
                    sender.sendMessage(Text.prefix(SentinelLite.lang.debug.notFlagged));
                }
            }
            default -> sender.sendMessage(Text.prefix(SentinelLite.lang.plugin.invalidSubCommand.formatted("debug")));
        }
    }

    private void handleSocialSpy(CommandSender sender) {
        if (!PlayerUtils.playerCheck(sender))
            return;
        if (!PlayerUtils.checkPermission(sender, "sentinel.socialspy"))
            return;
        Player p = (Player) sender;
        UUID senderID = p.getUniqueId();
        boolean enabled = spyMap.getOrDefault(senderID, false);
        if (!enabled) {
            sender.sendMessage(Text.prefix(SentinelLite.lang.socialSpy.enabled));
            spyMap.put(senderID, true);
        } else {
            sender.sendMessage(Text.prefix(SentinelLite.lang.socialSpy.disabled));
            spyMap.put(senderID, false);
        }
    }
}
