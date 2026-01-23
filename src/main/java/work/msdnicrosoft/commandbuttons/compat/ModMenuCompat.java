package work.msdnicrosoft.commandbuttons.compat;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import work.msdnicrosoft.commandbuttons.gui.CommandGUI;
import work.msdnicrosoft.commandbuttons.gui.WrapperCommandGUIScreen;

public class ModMenuCompat implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return screen -> {
            WrapperCommandGUIScreen guiScreen = new WrapperCommandGUIScreen(new CommandGUI());
            guiScreen.setParent(screen);
            return guiScreen;
        };
    }
}
