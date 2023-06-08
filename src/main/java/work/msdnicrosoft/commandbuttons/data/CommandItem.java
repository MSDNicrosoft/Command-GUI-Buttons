package work.msdnicrosoft.commandbuttons.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
public class CommandItem {
    @Setter
    private String displayName;
    private ArrayList<Text> raw;

    public void serialize(@NotNull JsonObject jsonObject) {
        jsonObject.addProperty("displayName", this.displayName);
        JsonArray raw = new JsonArray();
        this.raw.forEach(text -> raw.add(text.getText()));
        jsonObject.add("raw", raw);
    }
}
