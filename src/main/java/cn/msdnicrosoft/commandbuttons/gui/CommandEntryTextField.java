package cn.msdnicrosoft.commandbuttons.gui;

import io.github.cottonmc.cotton.gui.widget.WTextField;
import lombok.Setter;

import java.util.function.Consumer;

public class CommandEntryTextField extends WTextField {
    @Setter
    private Consumer<String> focusLostCallback;

    @Override
    public void releaseFocus() {
        super.releaseFocus();
    }

    @Override
    public void onFocusLost() {
        if (this.focusLostCallback != null) {
            focusLostCallback.accept(this.getText());
        }
        super.onFocusLost();
    }
}
