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
//#if MC >= 11900 && MC <= 11902
//#if MC >= 11901
//$$ import net.minecraft.Util;
//#endif
//$$ import net.minecraft.client.gui.chat.ClientChatPreview;
//$$ import net.minecraft.network.chat.Component;
//#endif

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
        //#if MC >= 11900 && MC <= 11902
        //$$ ClientChatPreview ccp = new ClientChatPreview(Minecraft.getInstance());
        //#if MC >= 11901
        //$$ Component component = Util.mapNullable(ccp.pull(text), ClientChatPreview.Preview::response);
        //#else
        //$$ Component component = ccp.pull(text);
        //#endif
        //#endif
        //#if MC >= 11900
        if (text.startsWith("/")) {
            //#if MC >= 11903
            player.connection.sendCommand(text.substring(1).trim());
            //#else
            //$$ player.commandSigned(text.substring(1), component);
            //#endif
        } else {
            //#if MC >= 11903
            player.connection.sendChat(text.trim());
            //#else
            //$$ player.chatSigned(text, component);
            //#endif
        }
        //#else
        //$$ player.chat(text.trim());
        //#endif
    }
}
