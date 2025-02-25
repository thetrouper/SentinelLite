package me.trouper.sentinellite.server.functions.chatfilter.profanity;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.trouper.sentinellite.server.functions.helpers.FilterHelpers;
import me.trouper.sentinellite.server.functions.chatfilter.FilterResponse;
import me.trouper.sentinellite.utils.ServerUtils;
import me.trouper.sentinellite.utils.Text;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

public class ProfanityResponse implements FilterResponse {

    private AsyncChatEvent event;
    private String originalMessage;
    private String processedMessage;
    private Severity severity;
    private boolean blocked;
    private boolean punished;

    public ProfanityResponse(AsyncChatEvent event, String originalMessage, String processedMessage, Severity severity, boolean blocked, boolean punished) {
        this.event = event;
        this.originalMessage = originalMessage;
        this.processedMessage = processedMessage;
        this.severity = severity;
        this.blocked = blocked;
        this.punished = punished;
    }

    @Override
    public Player getPlayer() {
        return event.getPlayer();
    }
    
    public AsyncChatEvent getEvent() {
        return event;
    }

    public void setEvent(AsyncChatEvent event) {
        this.event = event;
    }

    public String getOriginalMessage() {
        return originalMessage;
    }

    public void setOriginalMessage(String originalMessage) {
        this.originalMessage = originalMessage;
    }

    public String getProcessedMessage() {
        return processedMessage;
    }

    public void setProcessedMessage(String processedMessage) {
        this.processedMessage = processedMessage;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isPunished() {
        return punished;
    }

    public void setPunished(boolean punished) {
        this.punished = punished;
    }

    public static ProfanityResponse generate(AsyncChatEvent e) {
        if (e.isCancelled()) {
            ServerUtils.verbose("Profanity response opening: Event is canceled.");
        }

        String message = LegacyComponentSerializer.legacySection().serialize(e.message());
        Severity severity = Severity.SAFE;

        ProfanityResponse response = new ProfanityResponse(e,message,null,severity,false,false);

        String text = Text.removeFirstColor(message);
        response.setOriginalMessage(text);

        String lowercasedText = text.toLowerCase();
        response.setProcessedMessage(FilterHelpers.highlightProfanity(lowercasedText,"<hs>", "<he>"));
        ServerUtils.verbose("ProfanityFilter:  Lowercased: " + lowercasedText);

        String cleanedText = FilterHelpers.removeFalsePositives(lowercasedText);
        response.setProcessedMessage(FilterHelpers.highlightProfanity(cleanedText,"<hs>", "<he>"));
        ServerUtils.verbose(("ProfanityFilter: Removed False positives: " + cleanedText));

        response.setSeverity(FilterHelpers.checkProfanity(cleanedText, Severity.LOW));
        if (response.getSeverity() != Severity.SAFE) {
            return response;
        }

        String convertedText = FilterHelpers.convertLeetSpeakCharacters(cleanedText);
        response.setProcessedMessage(FilterHelpers.highlightProfanity(convertedText,"<hs>", "<he>"));
        ServerUtils.verbose(("ProfanityFilter: Leet Converted: " + convertedText));

        response.setSeverity(FilterHelpers.checkProfanity(convertedText, Severity.MEDIUM_LOW));
        if (response.getSeverity() != Severity.SAFE) {
            return response;
        }

        String strippedText = FilterHelpers.stripSpecialCharacters(convertedText);

        response.setProcessedMessage(FilterHelpers.highlightProfanity(strippedText,"<hs>", "<he>"));
        ServerUtils.verbose(("ProfanityFilter: Specials Removed: " + strippedText));

        response.setSeverity(FilterHelpers.checkProfanity(strippedText, Severity.MEDIUM));
        if (response.getSeverity() != Severity.SAFE) {
            return response;
        }

        String simplifiedText = FilterHelpers.simplifyRepeatingLetters(strippedText);
        response.setProcessedMessage(FilterHelpers.highlightProfanity(simplifiedText,"<hs>", "<he>"));
        ServerUtils.verbose(("ProfanityFilter: Removed Repeating: " + simplifiedText));

        response.setSeverity(FilterHelpers.checkProfanity(simplifiedText, Severity.MEDIUM_HIGH));
        if (response.getSeverity() != Severity.SAFE) {
            return response;
        }

        String finalText = FilterHelpers.removePeriodsAndSpaces(simplifiedText);
        response.setProcessedMessage(FilterHelpers.highlightProfanity(finalText,"<hs>", "<he>"));
        ServerUtils.verbose(("ProfanityFilter: Remove Punctuation: " + finalText));

        response.setSeverity(FilterHelpers.checkProfanity(finalText, Severity.HIGH));
        if (response.getSeverity() != Severity.SAFE) {
            return response;
        }

        ServerUtils.verbose(("ProfanityFilter: Finished " + finalText));
        if (e.isCancelled()) {
            ServerUtils.verbose("Profanity response closing: Event is canceled.");
        }
        return response;
    }
}
