package cn.msdnicrosoft.commandbuttons.data;

import cn.msdnicrosoft.commandbuttons.CommandButtonsReference;
import com.google.common.collect.Lists;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ConfigManager {
    @Getter
    private static final List<CommandItem> data = Lists.newArrayList();
    private static final File file = new File("./config/command-gui-buttons");

    private static void saveToFile() {
        Path path = ConfigManager.file.toPath().resolve("commands.json");

        if (data.isEmpty()) {
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                CommandButtonsReference.getLogger().error("Cannot delete empty file '{}': {}", path.toString(), e);
            }
            return;
        }

        String string = new GsonBuilder().setPrettyPrinting().create().toJson(ConfigManager.data);
        ConfigManager.checkDataRoot();
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(string);
        } catch (IOException e) {
            CommandButtonsReference.getLogger().error("Cannot write commands data: {}", e);
            CommandButtonsReference.getLogger().warn("Data: {}", string);
        }
    }

    private static void loadFromFile() {
        Path path = ConfigManager.file.toPath().resolve("commands.json");

        if (!Files.isRegularFile(path)) {
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            List<CommandItem> list = new GsonBuilder().create().fromJson(reader, new TypeToken<List<CommandItem>>(){}.getType());
            ConfigManager.data.clear();
            ConfigManager.data.addAll(list);
        } catch (IOException | JsonSyntaxException e) {
            CommandButtonsReference.getLogger().error("Cannot load commands data: {}", e);
        }
    }

    private static void checkDataRoot() {
        if (!ConfigManager.file.exists()) {
            ConfigManager.file.mkdirs();
        }
    }

    public static void add(CommandItem item) {
        ConfigManager.data.add(item);
        ConfigManager.saveToFile();
    }
    public static void remove(CommandItem item) {
        ConfigManager.data.remove(item);
        ConfigManager.saveToFile();
    }

    public static void init() {
        ConfigManager.loadFromFile();
    }
}
