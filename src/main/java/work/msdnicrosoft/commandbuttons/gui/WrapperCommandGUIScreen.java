package work.msdnicrosoft.commandbuttons.gui;

//#if MC < 12000
//$$ import com.mojang.blaze3d.vertex.PoseStack;
//#endif
import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

@Getter
@Setter
public class WrapperCommandGUIScreen extends CottonClientScreen {
    @Nullable
    private Runnable closeCallback;
    @Nullable
    private Screen parent;
    @Nullable
    private Runnable returnAction;

    public WrapperCommandGUIScreen(GuiDescription description) {
        super(description);
    }


    // Do not render background color in-game
    @Override
    public void renderBackground(
            //#if MC > 11904
            GuiGraphics guiGraphics
            //#if MC > 12001
            ,int mouseX,
            int mouseY,
            float partialTick
            //#endif
            //#else
            //$$ PoseStack poseStack
            //#if MC < 11904
            //$$ ,int vOffset
            //#endif
            //#endif
    ) {
        if (this.minecraft != null) {
            //#if MC >= 11906
            if (this.minecraft.level == null) {
                this.renderPanorama(guiGraphics, 0.32F);
            }
            this.renderBlurredBackground(
                    //#if MC >=12100
                    //#else
                    //$$ 0.32F
                    //#endif
            );
            this.renderMenuBackground(guiGraphics);
            //#else
            //$$ if (this.minecraft.level == null) {
            //$$ this.renderDirtBackground(
            //#if MC > 11904
            //$$ guiGraphics
            //#elseif MC > 11903
            //$$ poseStack
            //#else
            //$$ vOffset
            //#endif
            //$$ );
            //$$ }
            //#endif
        }
    }

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