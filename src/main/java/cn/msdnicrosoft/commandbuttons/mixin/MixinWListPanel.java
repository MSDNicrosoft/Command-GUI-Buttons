package cn.msdnicrosoft.commandbuttons.mixin;

import cn.msdnicrosoft.commandbuttons.gui.CommandEditListPanel;
import io.github.cottonmc.cotton.gui.widget.WClippedPanel;
import io.github.cottonmc.cotton.gui.widget.WListPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
//#if MC <= 11701
//$$ import org.objectweb.asm.Opcodes;
//$$ import org.spongepowered.asm.mixin.injection.ModifyArg;
//$$ import org.spongepowered.asm.mixin.injection.Redirect;
//#endif
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
    private int mgbuttons$index;

    //#if MC <= 11701
    //$$ @SuppressWarnings("ConstantConditions")
    //$$ @ModifyArg(
    //$$         method = "layout",
    //$$         at = @At(
    //$$                 value = "INVOKE",
    //$$                 target = "Lio/github/cottonmc/cotton/gui/widget/WScrollBar;setMaxValue(I)Lio/github/cottonmc/cotton/gui/widget/WScrollBar;"
    //$$         )
    //$$ )
    //$$ private int fixScrollBar(int maxValue) {
    //$$     if (((Object) this) instanceof CommandEditListPanel) {
    //$$         maxValue++;
    //$$     }
    //$$     return maxValue;
    //$$ }
    //#endif

    @SuppressWarnings({"ConstantConditions", "InvalidInjectorMethodSignature"})
    @ModifyVariable(
            method = "layout",
            at = @At(
                    value = "STORE"
            ),
            ordinal = 5
    )
    private int interceptionIndex(int index) {
        if (((Object) this) instanceof CommandEditListPanel) {
            this.mgbuttons$index = index;
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
            D d = this.data.get(this.mgbuttons$index);
            W w = this.configured.get(d);
            this.configurator.accept(d, w);
        }
    }

    //#if MC <= 11605
    //$$ @SuppressWarnings("ConstantConditions")
    //$$ @Redirect(
    //$$         method = "layout",
    //$$         at = @At(
    //$$                 value = "FIELD",
    //$$                 target = "Lio/github/cottonmc/cotton/gui/widget/WWidget;x:I",
    //$$                 opcode = Opcodes.PUTFIELD
    //$$         )
    //$$ )
    //$$ private void tweakWWidgetX(WWidget instance, int value) {
    //$$     if (((Object) this) instanceof CommandEditListPanel) {
    //$$         ((IWWidget) instance).setX(1);
    //$$     }
    //$$ }
    //#endif
}
