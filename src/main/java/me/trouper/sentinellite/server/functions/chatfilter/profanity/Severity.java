package me.trouper.sentinellite.server.functions.chatfilter.profanity;

import me.trouper.sentinellite.SentinelLite;

public enum Severity {
    LOW(SentinelLite.mainConfig.chat.profanityFilter.lowScore),
    MEDIUM_LOW(SentinelLite.mainConfig.chat.profanityFilter.mediumLowScore),
    MEDIUM(SentinelLite.mainConfig.chat.profanityFilter.mediumScore),
    MEDIUM_HIGH(SentinelLite.mainConfig.chat.profanityFilter.mediumHighScore),
    HIGH(SentinelLite.mainConfig.chat.profanityFilter.highScore),
    REGEX(SentinelLite.mainConfig.chat.profanityFilter.regexScore),
    SLUR(SentinelLite.mainConfig.chat.profanityFilter.highScore),
    SAFE(0);

    private final int score;

    Severity(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
