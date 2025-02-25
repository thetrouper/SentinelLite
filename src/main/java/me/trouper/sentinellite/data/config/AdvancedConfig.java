package me.trouper.sentinellite.data.config;

import io.github.itzispyder.pdk.utils.misc.config.JsonSerializable;
import me.trouper.sentinellite.SentinelLite;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvancedConfig implements JsonSerializable<AdvancedConfig> {
    public static String nonce = "%%__NONCE__%%";

    @Override
    public File getFile() {
        File file = new File(SentinelLite.dataFolder(), "/advanced-config.json");
        file.getParentFile().mkdirs();
        return file;
    }

    public Map<String, String> leetPatterns = new HashMap<>() {{
        put("0", "o");
        put("1", "i");
        put("3", "e");
        put("4", "a");
        put("5", "s");
        put("6", "g");
        put("7", "l");
        put("8","ate");
        put("$", "s");
        put("!", "i");
        put("|_|","u");
        put("|", "i");
        put("+", "t");
        put("#", "h");
        put("@", "a");
        put("<", "c");
        put("v", "u");
    }};
}
