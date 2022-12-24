package cn.msdnicrosoft.commandbuttons.compat.modmenu;

import cn.msdnicrosoft.commandbuttons.gui.CommandGUIScreen;
import cn.msdnicrosoft.commandbuttons.gui.CommandGUI;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (screen) -> new CommandGUIScreen(new CommandGUI());
    }
}
