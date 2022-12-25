package cn.msdnicrosoft.commandbuttons.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import org.jetbrains.annotations.Nullable;

public class WrapperCommandGUIScreen extends CottonClientScreen {
    @Nullable
    private final Runnable closeCallback;

    public WrapperCommandGUIScreen(GuiDescription description) {
        this(description, null);
    }

    public WrapperCommandGUIScreen(GuiDescription description, Runnable closeCallback) {
        super(description);
        this.closeCallback = closeCallback;
    }

    @Override
    protected void fillGradient(PoseStack poseStack, int i, int j, int k, int l, int m, int n) {
        // Do not use background color.
    }

    @Override
    public void removed() {
        if (this.closeCallback != null) {
            closeCallback.run();
        }
        super.removed();
    }
}