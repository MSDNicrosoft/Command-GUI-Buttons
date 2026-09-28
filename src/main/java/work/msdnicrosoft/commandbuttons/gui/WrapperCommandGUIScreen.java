package work.msdnicrosoft.commandbuttons.gui;

//#if MC < 12000
//$$ import com.mojang.blaze3d.vertex.PoseStack;
//#endif

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
//#if MC < 260102
//$$ import net.minecraft.client.gui.GuiGraphics;
//#endif
import net.minecraft.client.gui.screens.Screen;
//#if MC >= 12109
import net.minecraft.client.input.KeyEvent;
//#endif
import org.jetbrains.annotations.Nullable;
//#if MC >= 260300
import org.lwjgl.sdl.SDLKeycode;
//#else
//$$ import org.lwjgl.glfw.GLFW;
//#endif

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


    //#if MC < 260102
    // Do not render background color in-game
    //$$ @Override
    //$$ public void renderBackground(
    //$$         //#if MC > 11904
    //$$         GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick
    //$$         //#elseif MC >= 11904
    //$$         //$$ PoseStack poseStack
    //$$         //#else
    //$$         //$$ PoseStack poseStack, int vOffset
    //$$         //#endif
    //$$ ) {
    //$$     if (this.minecraft != null) {
    //$$         //#if MC >= 11906
    //$$         if (this.minecraft.level == null) {
    //$$             this.renderPanorama(guiGraphics, 0.32F);
    //$$         }
    //$$         this.renderBlurredBackground(
    //$$                 //#if MC > 12106
    //$$                 guiGraphics
    //$$                 //#elseif MC >= 12102
    //$$                 //#else
    //$$                 //$$ 0.32F
    //$$                 //#endif
    //$$         );
    //$$         this.renderMenuBackground(guiGraphics);
    //$$         //#else
    //$$         //$$ if (this.minecraft.level == null) {
    //$$         //$$ this.renderDirtBackground(
    //$$         //#if MC > 11904
    //$$         //$$ guiGraphics
    //$$         //#elseif MC > 11903
    //$$         //$$ poseStack
    //$$         //#else
    //$$         //$$ vOffset
    //$$         //#endif
    //$$         //$$ );
    //$$         //$$ }
    //$$         //#endif
    //$$     }
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
    public boolean keyPressed(
            //#if MC >= 12109
            KeyEvent input
            //#else
            //$$ int ch, int keyCode, int modifiers
            //#endif
    ) {
        // Support for returning to previous screens.
        boolean isEscapeKey =
                //#if MC >= 260300
                input.key() == SDLKeycode.SDLK_ESCAPE
                //#elseif MC >= 12109
                //$$ input.key() == GLFW.GLFW_KEY_ESCAPE
                //#else
                //$$ ch == GLFW.GLFW_KEY_ESCAPE
                //#endif
                ;
        if (isEscapeKey && this.parent != null) {
            //#if MC >= 260200
            Minecraft.getInstance().setScreenAndShow(this.parent);
            //#else
            //$$ Minecraft.getInstance().setScreen(this.parent);
            //#endif
            if (this.returnAction != null) {
                // Need to apply list update in the main screen.
                this.returnAction.run();
            }
            return true;
        }
        return super.keyPressed(
                //#if MC >= 12109
                input
                //#else
                //$$ ch, keyCode, modifiers
                //#endif
        );
    }
}