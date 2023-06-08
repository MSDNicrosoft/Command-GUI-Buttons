package work.msdnicrosoft.commandbuttons.mixin;

import io.github.cottonmc.cotton.gui.widget.WWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = WWidget.class, remap = false)
public interface IWWidget {
    @Accessor("x")
    void setX(int x);

    @Accessor("y")
    void setY(int y);
}
