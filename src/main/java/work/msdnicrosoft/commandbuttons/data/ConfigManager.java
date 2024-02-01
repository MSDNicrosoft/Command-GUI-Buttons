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
    private static final File file = new File("./config/command-gui-buttons");

    private static void saveToFile() {
        Path path = ConfigManager.file.toPath().resolve("commands.json");

        if (data.isEmpty()) {
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                CommandButtonsReference.getLogger().error("Cannot delete empty file '{}':", path.toString(), e);
            }
            return;
        }

        String string = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(CommandItem.class, new Serializer()).create().toJson(ConfigManager.data);
        ConfigManager.checkDataRoot();
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(string);
        } catch (IOException e) {
            CommandButtonsReference.getLogger().error("Cannot write commands data: ", e);
            CommandButtonsReference.getLogger().warn("Data: {}", string);
        }

        loadFromFile();
    }

    private static void loadFromFile() {
        Path path = ConfigManager.file.toPath().resolve("commands.json");

        if (!Files.isRegularFile(path)) {
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            List<CommandItem> list = new GsonBuilder().registerTypeAdapter(CommandItem.class, new Serializer()).create().fromJson(reader, new TypeToken<List<CommandItem>>() {
            }.getType());
            ConfigManager.data.clear();
            ConfigManager.data.addAll(list);
        } catch (IOException | JsonSyntaxException e) {
            CommandButtonsReference.getLogger().error("Cannot load commands data: ", e);
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
        if (!ConfigManager.file.exists()) {
            ConfigManager.file.mkdirs();
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
