package cn.msdnicrosoft.commandbuttons.gui;

//#if MC < 1194
//$$ import com.mojang.blaze3d.vertex.PoseStack;
//#endif
import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

public class WrapperCommandGUIScreen extends CottonClientScreen {
    @Nullable
    @Getter
    @Setter
    private Runnable closeCallback;
    @Getter
    @Setter
    @Nullable
    private Screen parent;
    @Getter
    @Setter
    @Nullable
    private Runnable returnAction;

    public WrapperCommandGUIScreen(GuiDescription description) {
        super(description);
    }

    //#if MC <= 1193
    //$$ @Override
    //$$ protected void fillGradient(PoseStack poseStack, int i, int j, int k, int l, int m, int n) {
    //$$    // Do not use background color.
    //$$ }
    //#endif

    @Override
    public void removed() {
        if (this.closeCallback != null) {
            closeCallback.run();
        }
        super.removed();
    }

    @Override
    public boolean keyPressed(int ch, int keyCode, int modifiers) {
        // Support for returning to previous screens.
        if (ch == GLFW.GLFW_KEY_ESCAPE && this.parent != null) {
            Minecraft.getInstance().setScreen(this.parent);
            if (this.returnAction != null) {
                // Need to apply list update in main screen.
                this.returnAction.run();
            }
            return true;
        }
        return super.keyPressed(ch, keyCode, modifiers);
    }
}