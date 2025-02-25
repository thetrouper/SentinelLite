package me.trouper.sentinellite.data.config;

import io.github.itzispyder.pdk.utils.misc.config.JsonSerializable;
import me.trouper.sentinellite.SentinelLite;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class MainConfig implements JsonSerializable<MainConfig> {

    public static String user = "%%__USER__%%";
    public static String username = "%%__USERNAME__%%";

    @Override
    public File getFile() {
        File file = new File(SentinelLite.dataFolder(), "/main-config.json");
        file.getParentFile().mkdirs();
        return file;
    }

    public boolean debugMode = false;

    public Plugin plugin = new Plugin();
    public Chat chat = new Chat();

    public class Plugin {
        public String prefix = "§d§lSentinel §7(§eLite§7) §8» §7";
        public String webhook = "https://discord.com/api/webhooks/id/token";
        public String lang = "en-us.json";
        public List<String> trustedPlayers = Arrays.asList(
                "049460f7-21cb-42f5-8059-d42752bf406f"
        );

        public boolean reopCommand = false;
        public String identifier = "My Server (Edit in main-config.json)";
    }

    public class Chat {
        public ProfanityFilter profanityFilter = new ProfanityFilter();

        public class ProfanityFilter {
            public boolean enabled = true;
            public boolean silent = false;
            public int lowScore = 0;
            public int mediumLowScore = 1;
            public int mediumScore = 3;
            public int mediumHighScore = 5;
            public int highScore = 7;
            public int regexScore = 4;
            public int scoreDecay = 3;
            public int punishScore = 20;
            public List<String> profanityPunishCommands = Arrays.asList(
                    "mute %player% 15m Do not attempt to bypass the Profanity Filter"
            );
            public List<String> strictPunishCommands = Arrays.asList(
                    "mute %player% 1h Discriminatory speech is not tolerated on this server!"
            );
        }
    }
}
