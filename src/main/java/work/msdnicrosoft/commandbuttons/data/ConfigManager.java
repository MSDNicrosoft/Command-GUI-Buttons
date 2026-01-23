package work.msdnicrosoft.commandbuttons.data;

import com.google.common.collect.Lists;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import work.msdnicrosoft.commandbuttons.CommandButtonsReference;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
    @Getter
    private static final List<CommandItem> data = Lists.newArrayList();
    private static final File configDir = new File("./config/command-gui-buttons");
    private static final Path configFilePath = configDir.toPath().resolve("commands.json");

    private static void saveToFile() {
        if (data.isEmpty()) {
            try {
                Files.deleteIfExists(configFilePath);
            } catch (IOException e) {
                CommandButtonsReference.getLogger().error("Failed to delete empty file '{}':", configFilePath.toString(), e);
            }
            return;
        }

        String string = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(CommandItem.class, new Serializer())
                .create()
                .toJson(ConfigManager.data);
        ConfigManager.checkDataRoot();
        try (BufferedWriter writer = Files.newBufferedWriter(configFilePath)) {
            writer.write(string);
        } catch (IOException e) {
            CommandButtonsReference.getLogger().error("Failed to write commands data: ", e);
            CommandButtonsReference.getLogger().warn("Data: {}", string);
        }

        loadFromFile();
    }

    private static void loadFromFile() {
        if (!Files.isRegularFile(configFilePath)) {
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(configFilePath)) {
            List<CommandItem> list = new GsonBuilder()
                    .registerTypeAdapter(CommandItem.class, new Serializer())
                    .create()
                    .fromJson(reader, new TypeToken<List<CommandItem>>() {}.getType());
            ConfigManager.data.clear();
            ConfigManager.data.addAll(list);
        } catch (IOException | JsonSyntaxException e) {
            CommandButtonsReference.getLogger().error("Failed to load commands data: ", e);
        }
    }

    static class Serializer implements JsonDeserializer<CommandItem>, JsonSerializer<CommandItem> {
        @Override
        public JsonElement serialize(CommandItem src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            src.serialize(jsonObject);
            return jsonObject;
        }

        @Override
        public CommandItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json.isJsonObject()) {
                JsonObject jsonObject = json.getAsJsonObject();
                return ConfigManager.createEntry(jsonObject);
            }
            return null;
        }
    }


    public static @NotNull CommandItem createEntry(@NotNull JsonObject jsonObject) {
        try {
            String displayName = jsonObject.has("displayName") ? jsonObject.get("displayName").getAsString() : "Unknown";
            JsonArray raw = jsonObject.has("raw") ? jsonObject.get("raw").getAsJsonArray() : null;
            ArrayList<Text> texts = Lists.newArrayList();
            if (raw != null) {
                raw.forEach(jsonElement -> texts.add(new Text(jsonElement.getAsString())));
            }
            return new CommandItem(displayName, texts);
        } catch (IllegalStateException | UnsupportedOperationException e) {
            return new CommandItem("Unknown", Lists.newArrayList());
        }
    }


    private static void checkDataRoot() {
        if (!ConfigManager.configDir.exists()) {
            ConfigManager.configDir.mkdirs();
        }
    }

    public static void add(CommandItem item) {
        ConfigManager.data.add(item);
        ConfigManager.save();
    }

    public static void remove(CommandItem item) {
        ConfigManager.data.remove(item);
        ConfigManager.save();
    }

    public static void init() {
        ConfigManager.loadFromFile();
    }

    public static void save() {
        ConfigManager.saveToFile();
    }
}
