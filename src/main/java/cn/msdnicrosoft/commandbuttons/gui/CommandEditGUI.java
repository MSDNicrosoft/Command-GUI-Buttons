package cn.msdnicrosoft.commandbuttons.gui;

import cn.msdnicrosoft.commandbuttons.data.CommandItem;
import cn.msdnicrosoft.commandbuttons.data.CommandItemDestination;
import cn.msdnicrosoft.commandbuttons.data.ConfigManager;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.function.BiConsumer;

public class CommandEditGUI extends LightweightGuiDescription {
    private final BiConsumer<String, CommandItemDestination> creator = this::defBtnBehavior;

    private final WGridPanel root = new WGridPanel(5);
    private final CommandItem item;
    private final WTextField displayName = new WTextField().setSuggestion(Component.translatable("mgbuttons.gui.edit.name"));
    private final WTextField input = new WTextField().setSuggestion(Component.translatable("mgbuttons.gui.edit.type")).setMaxLength(Integer.MAX_VALUE);
    private final WButton addBtn = new WButton(Component.literal("+")).setOnClick(this::addBtnCallback);

    private final CommandEditListPanel<String, CommandItemDestination> raw;
    private boolean editMode;

    public CommandEditGUI(CommandItem item) {
        this(item, false);
    }

    public CommandEditGUI(CommandItem item, boolean editMode) {
        this.item = item;
        this.raw = new CommandEditListPanel<>(this.item.getRaw(), CommandItemDestination::new, this.creator);
        this.editMode = editMode;
        this.setupRoot();
        this.setRootPanel(root);
    }

    private void setupRoot() {
        this.root.setSize(250, 260);
        this.displayName.setText(this.item.getDisplayName());
        this.displayName.setChangedListener(this.item::setDisplayName);
        this.root.add(this.displayName, 1, 1, 48, 4);
        this.root.add(this.raw, 0, 6, 49, 40);
        this.root.add(this.input, 1, 47, 43, 4);
        this.root.add(this.addBtn, 45, 47, 4, 4);
    }

    @Override
    public void addPainters() {
        super.addPainters();
        this.rootPanel.setBackgroundPainter(BackgroundPainter.createColorful(0x4D000000));
    }

    private void addBtnCallback() {
        if (!this.input.getText().trim().isEmpty()) {
            this.item.getRaw().add(input.getText().trim());
            this.input.setText("");
            this.raw.layout();
        }
    }

    private void defBtnBehavior(String string, @NotNull CommandItemDestination commandItemDestination) {
        int index = this.item.getRaw().indexOf(string);
        commandItemDestination.getCommand().setMaxLength(Integer.MAX_VALUE);
        commandItemDestination.getCommand().setText(string);
        commandItemDestination.getCommand().setReleaseFocusCallback((s -> this.item.getRaw().set(index, s)));
        commandItemDestination.getCommand().setSuggestion(Component.translatable("mgbuttons.gui.edit.type_with_index", index + 1));
        if (this.item.getRaw().indexOf(string) == 0) {
            commandItemDestination.getUp().setEnabled(false);
        } else {
            commandItemDestination.getUp().setEnabled(true);
            commandItemDestination.getUp().setOnClick(() -> {
                Collections.swap(this.item.getRaw(), index, index - 1);
                this.raw.layout();
            });
        }
        if (this.item.getRaw().indexOf(string) == this.item.getRaw().size() - 1) {
            commandItemDestination.getDown().setEnabled(false);
        } else {
            commandItemDestination.getDown().setEnabled(true);
            commandItemDestination.getDown().setOnClick(() -> {
                Collections.swap(this.item.getRaw(), index, index + 1);
                this.raw.layout();
            });
        }
        commandItemDestination.getDelete().setOnClick(() -> {
            this.item.getRaw().remove(string);
            this.raw.layout();
        });
    }

    public void saveData() {
        if (!this.editMode) {
            ConfigManager.add(item);
            this.editMode = true;
        }
        ConfigManager.save();
    }
}
