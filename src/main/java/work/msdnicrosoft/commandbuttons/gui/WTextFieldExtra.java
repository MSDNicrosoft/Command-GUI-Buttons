package work.msdnicrosoft.commandbuttons.gui;

import io.github.cottonmc.cotton.gui.widget.WTextField;
import lombok.Setter;

import java.util.function.Consumer;

public class WTextFieldExtra extends WTextField {
    @Setter
    private Consumer<String> focusLostCallback;

    //#if MC <= 11605
    //$$ private Consumer<String> listenerEx;
    //#endif

    @Override
    public void onFocusLost() {
        if (this.focusLostCallback != null) {
            focusLostCallback.accept(this.getText());
        }
        super.onFocusLost();
    }

    //#if MC <= 11605
    //$$ @Override
    //$$ public WTextField setChangedListener(Consumer<String> listener) {
    //$$     this.listenerEx = listener;
    //$$     return super.setChangedListener(listener);
    //$$ }
    //$$
    //$$ @Override
    //$$ public void onKeyPressed(int ch, int key, int modifiers) {
    //$$     super.onKeyPressed(ch, key, modifiers);
    //$$     if (this.listenerEx != null) {
    //$$         this.listenerEx.accept(this.getText());
    //$$     }
    //$$ }
    //#endif
}
