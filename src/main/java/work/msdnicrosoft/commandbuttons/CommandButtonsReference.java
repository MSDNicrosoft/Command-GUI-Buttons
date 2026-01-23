package work.msdnicrosoft.commandbuttons;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandButtonsReference {
    @Getter
    private static final String modIdentifier = "@MOD_ID@";

    @Getter
    private static final Logger logger = LogManager.getLogger(modIdentifier);
}
