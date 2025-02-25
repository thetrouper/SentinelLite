package me.trouper.sentinellite.data.config;

import io.github.itzispyder.pdk.utils.misc.config.JsonSerializable;
import me.trouper.sentinellite.SentinelLite;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class StrictConfig implements JsonSerializable<StrictConfig> {
    @Override
    public File getFile() {
        File file = new File(SentinelLite.dataFolder(), "/strict.json");
        file.getParentFile().mkdirs();
        return file;
    }

    public String regexStrict = "";
    public boolean useRegex = false;
    public List<String> strict = Arrays.asList(
            "nigg",
            "niger",
            "nlg",
            "tranny",
            "fag",
            "beaner",
            "retard"
    );
}
