package cn.msdnicrosoft.commandbuttons.gui;

import io.github.cottonmc.cotton.gui.widget.WTextField;
import lombok.Setter;

import java.util.function.Consumer;

public class CommandEntryTextField extends WTextField {
    @Setter
    private Consumer<String> releaseFocusCallback;

    @Override
    public void releaseFocus() {
        if (this.releaseFocusCallback != null) {
            releaseFocusCallback.accept(this.getText());
        }
        super.releaseFocus();
    }
}
