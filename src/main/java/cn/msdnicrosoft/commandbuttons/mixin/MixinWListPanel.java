package cn.msdnicrosoft.commandbuttons.mixin;

import cn.msdnicrosoft.commandbuttons.gui.CommandEditListPanel;
import io.github.cottonmc.cotton.gui.widget.WClippedPanel;
import io.github.cottonmc.cotton.gui.widget.WListPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

@Mixin(value = WListPanel.class, remap = false)
public abstract class MixinWListPanel<D, W extends WWidget> extends WClippedPanel {
    @Shadow
    protected List<D> data;

    @Shadow
    protected HashMap<D, W> configured;

    @Shadow
    protected BiConsumer<D, W> configurator;
    private int mgbutton$index;

    @SuppressWarnings("ConstantConditions")
    @ModifyVariable(
            method = "layout",
            at = @At(
                    value = "STORE"
            ),
            ordinal = 5
    )
    private int interceptionIndex(int index) {
        if (((Object) this) instanceof CommandEditListPanel) {
            this.mgbutton$index = index;
        }
        return index;
    }

    @SuppressWarnings("ConstantConditions")
    @Inject(
            method = "layout",
            at = @At(
                    value = "INVOKE",
                    target = "Lio/github/cottonmc/cotton/gui/widget/WWidget;canResize()Z",
                    ordinal = 3
            )
    )
    private void forceApplySettingsEveryTime(CallbackInfo ci) {
        if (((Object) this) instanceof CommandEditListPanel) {
            D d = this.data.get(this.mgbutton$index);
            W w = this.configured.get(d);
            this.configurator.accept(d, w);
        }
    }
}