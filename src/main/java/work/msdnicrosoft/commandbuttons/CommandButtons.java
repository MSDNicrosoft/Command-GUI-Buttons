package work.msdnicrosoft.commandbuttons;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
//#if MC >= 260102
import net.fabricmc.fabric.impl.client.keymapping.KeyMappingRegistryImpl;
//#else
//$$ import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
//#endif
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.jetbrains.annotations.NotNull;
import work.msdnicrosoft.commandbuttons.data.ConfigManager;
import work.msdnicrosoft.commandbuttons.gui.CommandGUI;
import work.msdnicrosoft.commandbuttons.gui.WrapperCommandGUIScreen;
//#if MC >= 11900 && MC <= 11902
//$$ import net.minecraft.Util;
//$$ import net.minecraft.client.gui.chat.ClientChatPreview;
//$$ import net.minecraft.network.chat.Component;
//#endif

public class CommandButtons implements ModInitializer {
    @Override
    public void onInitialize() {
        ConfigManager.init();

        //#if MC >= 260102
        KeyMapping keyBinding = KeyMappingRegistryImpl.registerKeyMapping(CommandButtonsReference.OPEN_GUI_KEY_MAPPING);
        //#else
        //$$ KeyMapping keyBinding = KeyBindingHelper.registerKeyBinding(CommandButtonsReference.OPEN_GUI_KEY_MAPPING);
        //#endif

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
        //$$ Component component = Util.mapNullable(ccp.pull(text), ClientChatPreview.Preview::response);
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
