package me.trouper.sentinellite.utils;

import io.github.itzispyder.pdk.utils.FileValidationUtils;
import me.trouper.sentinellite.SentinelLite;
import org.bukkit.inventory.ItemStack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

    public static String createNBTLog(ItemStack i)  {
        ServerUtils.verbose("FileUtils: Creating NBT log");

        String item = i.getType().name().toLowerCase() + i.getItemMeta().getAsString();

        String fileName = "nbt_log-" + Randomizer.generateID();

        File dataFolder = SentinelLite.dataFolder();

        File loggedNBTFolder = new File(dataFolder,"LoggedNBT");
        if (!loggedNBTFolder.exists()) {
            loggedNBTFolder.mkdirs();
        }

        File file = new File(loggedNBTFolder, fileName + ".txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.append(item);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }


    public static String createCommandLog(String command)  {

        String fileName = "command_log-" + Randomizer.generateID();
        File file = new File(SentinelLite.dataFolder() + "/LoggedCommands/" + fileName + ".txt");
        FileValidationUtils.validate(file);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.append(command);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileName;
    }
}
