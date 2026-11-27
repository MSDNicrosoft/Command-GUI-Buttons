package work.msdnicrosoft.commandbuttons.data;

import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import lombok.Getter;
import work.msdnicrosoft.commandbuttons.gui.DraggableButton;

public class CommandDestination extends WPlainPanel {
    @Getter
    DraggableButton button = new DraggableButton();

    public CommandDestination() {
        //#if MC > 11605
        this.add(this.button, 0, 0, 78, 20);
        //#else
        //$$ this.add(this.button, 0, 0, 80, 20);
        //#endif
    }
}
