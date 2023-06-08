package work.msdnicrosoft.commandbuttons.gui;

import io.github.cottonmc.cotton.gui.widget.WListPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class CommandEditListPanel<D, W extends WWidget> extends WListPanel<D, W> {
    public CommandEditListPanel(List<D> data, Supplier<W> supplier, BiConsumer<D, W> configurator) {
        super(data, supplier, configurator);
    }
}
