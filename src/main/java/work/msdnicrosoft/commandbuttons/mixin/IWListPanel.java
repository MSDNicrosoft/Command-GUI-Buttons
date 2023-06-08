package work.msdnicrosoft.commandbuttons.mixin;

import io.github.cottonmc.cotton.gui.widget.WListPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = WListPanel.class, remap = false)
public interface IWListPanel<W extends WWidget> {
    @Invoker
    W invokeCreateChild();
}
