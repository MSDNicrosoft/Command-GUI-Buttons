package cn.msdnicrosoft.commandbuttons;


import cn.msdnicrosoft.commandbuttons.gui.ButtonGUI;
import cn.msdnicrosoft.commandbuttons.gui.ButtonGUIScreen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.json.simple.JSONObject;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class CommandButtons implements ModInitializer {

    public static final String MOD_ID = "mgbuttons";
    private static ArrayList<JSONObject> masterCommList;

    public static void runCommand(String command) {
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.sendChatMessage(command);
    }

    // Assign masterCommList to JSONArray<objects> (from commands.json). Runs once.
    static void initArray() {
        masterCommList = ConfigFile.getArrayFromJsonFile();
        // If commands.json doesn't exist yet, start a global list variable for future creation
        if (masterCommList == null) {
            setMasterCommList(new ArrayList<>());
        }
    }

    public static ArrayList<JSONObject> getMasterCommList() {
        return masterCommList;
    }

    public static void setMasterCommList(ArrayList<JSONObject> commList) {
        masterCommList = commList;
    }

    @Override
    public void onInitialize() {
        assignGuiToKey();
        initArray();
    }

    private void assignGuiToKey() {
        // Currently, assigns to the G key
        KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.commandbuttons.opengui", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_G, // The keycode of the key
                "gui.commandbuttons.mgbuttons" // The translation key of the keybinding's category.
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                MinecraftClient.getInstance().setScreen(new ButtonGUIScreen(new ButtonGUI()));
                // client.player.closeScreen();
            }
        });
    }

}

