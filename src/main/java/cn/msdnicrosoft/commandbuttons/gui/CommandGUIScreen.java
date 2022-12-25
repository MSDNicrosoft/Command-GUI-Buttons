package cn.msdnicrosoft.commandbuttons.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;

public class CommandGUIScreen extends CottonClientScreen {
    public CommandGUIScreen(GuiDescription description) {
        super(description);
    }

    @Override
    protected void fillGradient(PoseStack poseStack, int i, int j, int k, int l, int m, int n) {
        // Do not use background color.
    }
}