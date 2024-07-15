package work.msdnicrosoft.commandbuttons;

import lombok.Getter;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandButtonsReference {
    private static final String currentModIdentifier = "@MOD_ID@-@MINECRAFT_VERSION_ID@";
    @Getter
    private static final String modIdentifier = "@MOD_ID@";
    @Getter
    private static final String modName = FabricLoader.getInstance().getModContainer(currentModIdentifier)
            .orElseThrow(RuntimeException::new).getMetadata().getName();
    @Getter
    private static final String modVersion = FabricLoader.getInstance().getModContainer(currentModIdentifier)
            .orElseThrow(RuntimeException::new).getMetadata().getVersion().getFriendlyString();
    @Getter
    private static final Logger logger = LogManager.getLogger(modIdentifier);
}
