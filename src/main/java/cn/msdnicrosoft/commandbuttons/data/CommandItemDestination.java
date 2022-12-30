package cn.msdnicrosoft.commandbuttons.data;

import cn.msdnicrosoft.commandbuttons.compat.minecraft.ComponentCompatApi;
import cn.msdnicrosoft.commandbuttons.gui.CommandEntryTextField;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import lombok.Getter;

@Getter
public class CommandItemDestination extends WPlainPanel {
    private final CommandEntryTextField command = new CommandEntryTextField();
    private final WButton up = new WButton().setLabel(ComponentCompatApi.literal("↑"));
    private final WButton down = new WButton().setLabel(ComponentCompatApi.literal("↓"));
    private final WButton delete = new WButton().setLabel(ComponentCompatApi.literal("×"));

    public CommandItemDestination() {
        this.add(this.command, 1, 0, 159, 20);
        this.add(this.up, 165, 0, 20, 20);
        this.add(this.down, 185, 0, 20, 20);
        this.add(this.delete, 205, 0, 20, 20);
    }
}
