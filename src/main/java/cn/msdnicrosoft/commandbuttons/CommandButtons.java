package cn.msdnicrosoft.commandbuttons;

import cn.msdnicrosoft.commandbuttons.gui.ButtonGUI;
import cn.msdnicrosoft.commandbuttons.gui.ButtonGUIScreen;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.Util;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.ClientChatPreview;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class CommandButtons implements ModInitializer {
    public static final String MOD_ID = "mgbuttons";
    private static ArrayList<JSONObject> masterCommList;
    private static final Minecraft mc = Minecraft.getInstance();
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static void send(String text) {
        if (text.startsWith("/")) {
            send(text.substring(1), true);
        } else {
            send(text, false);
        }
    }

    public static void send(String text, boolean useCommand) {
        LocalPlayer player = mc.player;
        ClientChatPreview chatPreview = new ClientChatPreview(mc);
        Component component = Util.mapNullable(chatPreview.pull(text), ClientChatPreview.Preview::response);
        if (player == null) {
            return;
        }
        if (useCommand) {
            player.commandSigned(text, component);
        } else {
            player.chatSigned(text, component);
        }
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
        KeyMapping keyBinding = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "mgbuttons.key.opengui", // The translation key of the keybinding's name
                InputConstants.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_G, // The keycode of the key
                "mgbuttons.key.category" // The translation key of the keybinding's category.
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.consumeClick()) {
                Minecraft.getInstance().setScreen(new ButtonGUIScreen(new ButtonGUI()));
                // client.player.closeScreen();
            }
        });
    }
}