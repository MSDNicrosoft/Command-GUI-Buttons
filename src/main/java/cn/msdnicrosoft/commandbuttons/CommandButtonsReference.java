package cn.msdnicrosoft.commandbuttons;

import lombok.Getter;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandButtonsReference {
    @Getter
    private static final String modIdentifier = "mgbuttons";
    @Getter
    private static final String modName = FabricLoader.getInstance().getModContainer(modIdentifier)
            .orElseThrow(RuntimeException::new).getMetadata().getName();
    @Getter
    private static final String modVersion = FabricLoader.getInstance().getModContainer(modIdentifier)
            .orElseThrow(RuntimeException::new).getMetadata().getVersion().getFriendlyString();
    @Getter
    private static final Logger logger = LogManager.getLogger(modIdentifier);
}
