package cn.msdnicrosoft.commandbuttons.data;

import cn.msdnicrosoft.commandbuttons.compat.minecraft.ComponentCompatApi;
import cn.msdnicrosoft.commandbuttons.gui.WTextFieldExtra;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import lombok.Getter;

@Getter
public class CommandItemDestination extends WPlainPanel {
    private final WTextFieldExtra command = new WTextFieldExtra();
    private final WButton up = new WButton().setLabel(ComponentCompatApi.literal("↑"));
    private final WButton down = new WButton().setLabel(ComponentCompatApi.literal("↓"));
    private final WButton delete = new WButton().setLabel(ComponentCompatApi.literal("×"));

    public CommandItemDestination() {
        //#if MC > 11605
        this.add(this.command, 1, 0, 164, 20);
        this.add(this.up, 170, 0, 20, 20);
        this.add(this.down, 190, 0, 20, 20);
        this.add(this.delete, 210, 0, 20, 20);
        //#else
        //$$ this.add(this.command, 0, 0, 170, 20);
        //$$ this.add(this.up, 175, 0, 20, 20);
        //$$ this.add(this.down, 195, 0, 20, 20);
        //$$ this.add(this.delete, 215, 0, 20, 20);
        //#endif
    }
}
