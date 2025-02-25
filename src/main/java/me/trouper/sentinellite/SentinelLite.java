package me.trouper.sentinellite;

import io.github.itzispyder.pdk.PDK;
import io.github.itzispyder.pdk.utils.misc.config.JsonSerializable;
import me.trouper.sentinellite.data.config.*;
import me.trouper.sentinellite.data.config.lang.LanguageFile;
import me.trouper.sentinellite.server.commands.*;
import me.trouper.sentinellite.server.events.*;
import me.trouper.sentinellite.server.functions.chatfilter.profanity.ProfanityFilter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public final class SentinelLite extends JavaPlugin {

    public static final Logger log = Bukkit.getLogger();
    private static SentinelLite instance;
    public static LanguageFile lang;

    private static final File dataFolder = new File("plugins/SentinelLite");
    private static final File violationcfg = new File(SentinelLite.dataFolder(),"/violation-config.json");
    private static final File cfgfile = new File(SentinelLite.dataFolder(),"/main-config.json");
    private static final File nbtcfg = new File(SentinelLite.dataFolder(), "/nbt-config.json");
    private static final File strctcfg = new File(SentinelLite.dataFolder(), "/strict.json");
    private static final File swrcfg = new File(SentinelLite.dataFolder(), "/swears.json");
    private static final File fpcfg = new File(SentinelLite.dataFolder(), "/false-positives.json");
    private static final File advcfg = new File(SentinelLite.dataFolder(), "/advanced-config.json");

    public static ViolationConfig violationConfig = JsonSerializable.load(violationcfg, ViolationConfig.class, new ViolationConfig());
    public static MainConfig mainConfig = JsonSerializable.load(cfgfile, MainConfig.class, new MainConfig());
    public static FPConfig fpConfig = JsonSerializable.load(fpcfg, FPConfig.class, new FPConfig());
    public static SwearsConfig swearConfig = JsonSerializable.load(swrcfg, SwearsConfig.class, new SwearsConfig());
    public static StrictConfig strictConfig = JsonSerializable.load(strctcfg, StrictConfig.class, new StrictConfig());
    public static NBTConfig nbtConfig = JsonSerializable.load(nbtcfg, NBTConfig.class, new NBTConfig());
    public static AdvancedConfig advConfig = JsonSerializable.load(advcfg, AdvancedConfig.class, new AdvancedConfig());

    @Override
    public void onEnable() {
        log.info("Initializing PDK");
        PDK.init(this);

        log.info("Instantiating plugin");
        instance = this;

        SentinelLite.log.info("\n]======----- Loading Sentinel Lite! -----======[");

        SentinelLite.log.info("Reading Persistent files...");

        SentinelLite.getInstance().loadConfig();

        SentinelLite.log.info("Language Status: (%s)".formatted(SentinelLite.lang.brokenLang));


        SentinelLite.log.info("Registering Commands");
        // Commands
        new SentinelLiteCommand().register();
        new MessageCommand().register();
        new ReplyCommand().register();
        new ReopCommand().register();

        SentinelLite.log.info("Registering Events...");
        // Events
        new CommandBlockEdit().register();
        new CommandBlockMinecartPlace().register();
        new CommandBlockMinecartUse().register();
        new CommandBlockPlace().register();
        new CommandBlockUse().register();
        new ChatEvent().register();
        new CommandExecute().register();
        new CreativeHotbar().register();

        // Scheduled timers
        Bukkit.getScheduler().runTaskTimer(SentinelLite.getInstance(), ProfanityFilter::decayScore,0,1200);

        SentinelLite.log.info("""
                Finished!
                 ____                   __                        ___     \s
                /\\  _`\\                /\\ \\__  __                /\\_ \\    \s
                \\ \\,\\L\\_\\     __    ___\\ \\ ,_\\/\\_\\    ___      __\\//\\ \\   \s
                 \\/_\\__ \\   /'__`\\/' _ `\\ \\ \\/\\/\\ \\ /' _ `\\  /'__`\\\\ \\ \\  \s
                   /\\ \\L\\ \\/\\  __//\\ \\/\\ \\ \\ \\_\\ \\ \\/\\ \\/\\ \\/\\  __/ \\_\\ \\_\s
                   \\ `\\____\\ \\____\\ \\_\\ \\_\\ \\__\\\\ \\_\\ \\_\\ \\_\\ \\____\\/\\____\\
                    \\/_____/\\/____/\\/_/\\/_/\\/__/ \\/_/\\/_/\\/_/\\/____/\\/____/
                     ]====---- Lite Anti-Grief & Profanity Filter ----====[""");
    }

    public void loadConfig() {
        // Init
        mainConfig = JsonSerializable.load(cfgfile,MainConfig.class,new MainConfig());
        advConfig = JsonSerializable.load(advcfg,AdvancedConfig.class,new AdvancedConfig());
        fpConfig = JsonSerializable.load(fpcfg,FPConfig.class,new FPConfig());
        strictConfig = JsonSerializable.load(strctcfg,StrictConfig.class,new StrictConfig());
        swearConfig = JsonSerializable.load(swrcfg,SwearsConfig.class,new SwearsConfig());
        nbtConfig = JsonSerializable.load(nbtcfg,NBTConfig.class,new NBTConfig());
        violationConfig = JsonSerializable.load(violationcfg,ViolationConfig.class,new ViolationConfig());

        // Save
        mainConfig.save();
        advConfig.save();
        fpConfig.save();
        strictConfig.save();
        swearConfig.save();
        nbtConfig.save();
        violationConfig.save();

        log.info("Loading Dictionary (%s)...".formatted(SentinelLite.mainConfig.plugin.lang));

        lang = JsonSerializable.load(LanguageFile.PATH,LanguageFile.class,new LanguageFile());
        lang.save();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        log.info("Sentinel has disabled! (%s) Your server is now no longer protected!".formatted(getDescription().getVersion()));
    }

    public static SentinelLite getInstance() {
        return instance;
    }

    public static File dataFolder() {
        return dataFolder;
    }
}
