package cn.msdnicrosoft.commandbuttons.gui;

import cn.msdnicrosoft.commandbuttons.CommandButtons;
import cn.msdnicrosoft.commandbuttons.data.CommandDestination;
import cn.msdnicrosoft.commandbuttons.data.CommandItem;
import cn.msdnicrosoft.commandbuttons.data.ConfigManager;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.WToggleButton;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public class CommandGUI extends LightweightGuiDescription {
    private final BiConsumer<CommandItem, CommandDestination> creator = this::defBtnBehavior;

    private final WTextField search = new WTextField().setSuggestion(Component.translatable("mgbuttons.gui.search"));
    private final WTextField defName = new WTextField().setSuggestion(Component.translatable("mgbuttons.gui.name"));
    private final WTextField defRaw = new WTextField().setSuggestion(Component.translatable("mgbuttons.gui.command"));
    private final WToggleButton deleteMode = new WToggleButton(Component.translatable("mgbuttons.gui.delete"));
    private final WButton addBtn = new WButton(Component.literal("+")).setOnClick(this::addCallBack);

    private final CommandListPanel<CommandItem, CommandDestination> commandPanel = new CommandListPanel<>(
            ConfigManager.getData(), CommandDestination::new, creator, this.search);
    private final WGridPanel root = new WGridPanel(5);

    public CommandGUI() {
        this.setupRoot();
        this.setRootPanel(root);
        this.createCommandPanel();
    }

    private void setupRoot() {
        this.root.setSize(350, 240);
        this.root.add(this.search, 1, 1, 68, 2);
        this.root.add(this.commandPanel, 1, 6, 68, 36);
        this.root.add(this.defName, 1, 43, 17, 2);
        this.root.add(this.defRaw, 20, 43, 34, 2);
        this.root.add(this.addBtn, 55, 43, 4, 4);
        this.root.add(this.deleteMode, 60, 43, 4, 4);
        this.rootPanel.setBackgroundPainter(BackgroundPainter.createColorful(0x4D000000));
    }

    private void createCommandPanel() {
        this.commandPanel.setBackgroundPainter(BackgroundPainter.createColorful(0x00000000));
        this.search.setChangedListener(s -> this.commandPanel.layout());
    }

    private void defBtnBehavior(@NotNull CommandItem item, @NotNull CommandDestination destination) {
        destination.getButton().setLabel(Component.literal(item.getDisplayName()));
        destination.getButton().setOnClick(() -> {
            if (this.deleteMode.getToggle()) {
                ConfigManager.remove(item);
                this.commandPanel.layout();
            } else {
                CommandButtons.send(item.getRaw());
            }
        });
    }

    private void addCallBack() {
        if (this.defName.getText().trim().isEmpty() || this.defRaw.getText().trim().isEmpty()) {
            return;
        }
        if (ConfigManager.getData().stream().anyMatch(commandItem ->
                commandItem.getDisplayName().equals(this.defName.getText().trim()) &&
                        commandItem.getRaw().equals(this.defRaw.getText().trim()))) {
            return;
        }
        CommandItem item = new CommandItem(this.defName.getText().trim(), this.defRaw.getText().trim());
        ConfigManager.add(item);
        this.commandPanel.layout();
        this.defName.setText("");
        this.defRaw.setText("");
    }

    @Override
    public void addPainters() {
        super.addPainters();
    }
}
