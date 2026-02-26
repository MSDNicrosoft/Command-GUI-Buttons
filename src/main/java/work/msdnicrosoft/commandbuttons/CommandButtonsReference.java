package work.msdnicrosoft.commandbuttons;

import com.mojang.blaze3d.platform.InputConstants;
import lombok.Getter;
import net.minecraft.client.KeyMapping;
//#if MC >= 12111
import net.minecraft.resources.Identifier;
//#elseif MC >= 12109
//$$ import net.minecraft.resources.ResourceLocation;
//#endif
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

public class CommandButtonsReference {
    @Getter
    private static final String modIdentifier = "@MOD_ID@";

    @Getter
    private static final Logger logger = LogManager.getLogger(modIdentifier);

    public static final KeyMapping OPEN_GUI_KEY_MAPPING = new KeyMapping(
            "mgbuttons.key.opengui",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            //#if MC >= 12111
            KeyMapping.Category.register(Identifier.fromNamespaceAndPath("mgbuttons", "category"))
            //#elseif MC >= 12109
            //$$ KeyMapping.Category.register(ResourceLocation.fromNamespaceAndPath("mgbuttons", "category"))
            //#else
            //$$ "key.category.mgbuttons.category"
            //#endif
    );
}
