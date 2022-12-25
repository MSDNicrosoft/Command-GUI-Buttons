package cn.msdnicrosoft.commandbuttons;

import cn.msdnicrosoft.commandbuttons.data.ConfigManager;
import cn.msdnicrosoft.commandbuttons.gui.CommandGUI;
import cn.msdnicrosoft.commandbuttons.gui.WrapperCommandGUIScreen;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

public class CommandButtons implements ModInitializer {
    @Override
    public void onInitialize() {
        ConfigManager.init();
        KeyMapping keyBinding = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "mgbuttons.key.opengui",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "mgbuttons.key.category"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.consumeClick()) {
                Minecraft.getInstance().setScreen(new WrapperCommandGUIScreen(new CommandGUI()));
            }
        });
    }

    public static void send(@NotNull String text) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || text.trim().isEmpty()) {
            return;
        }
        if (text.startsWith("/")) {
            player.connection.sendCommand(text.substring(1).trim());
        } else {
            player.connection.sendChat(text.trim());
        }
    }
}
