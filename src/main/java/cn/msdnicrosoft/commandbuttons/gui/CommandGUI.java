package cn.msdnicrosoft.commandbuttons.gui;

import cn.msdnicrosoft.commandbuttons.CommandButtons;
import cn.msdnicrosoft.commandbuttons.data.CommandDestination;
import cn.msdnicrosoft.commandbuttons.data.CommandItem;
import cn.msdnicrosoft.commandbuttons.data.ConfigManager;
import com.google.common.collect.Lists;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.WToggleButton;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public class CommandGUI extends LightweightGuiDescription {
    private final BiConsumer<CommandItem, CommandDestination> creator = this::defBtnBehavior;

    private final WTextField search = new WTextField().setSuggestion(Component.translatable("mgbuttons.gui.main.search"));
    private final WButton addBtn = new WButton(Component.literal("+")).setOnClick(this::addCallBack);
    private final WToggleButton editBtn = new WToggleButton(Component.translatable("mgbuttons.gui.main.edit")).setColor(0xFFFFFF, 0xFFFFFF).setOnToggle(this::editBtnCallback);
    private final WToggleButton deleteBtn = new WToggleButton(Component.translatable("mgbuttons.gui.main.delete")).setColor(0xFFFFFF, 0xFFFFFF).setOnToggle(this::deleteBtnCallback);

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
        this.root.add(this.addBtn, 1, 43, 4, 4);
        this.root.add(this.editBtn, 6, 43, 4, 4);
        this.root.add(this.deleteBtn, 25, 43, 4, 4);
    }

    private void createCommandPanel() {
        this.commandPanel.setBackgroundPainter(BackgroundPainter.createColorful(0x00000000));
        this.search.setChangedListener(s -> this.commandPanel.layout());
    }

    private void defBtnBehavior(@NotNull CommandItem item, @NotNull CommandDestination destination) {
        destination.getButton().setLabel(Component.literal(item.getDisplayName()));
        destination.getButton().setOnClick(() -> {
            if (this.editBtn.getToggle()) {
                CommandEditGUI gui = new CommandEditGUI(item, true);
                Minecraft.getInstance().setScreen(new WrapperCommandGUIScreen(gui, gui::saveData));
            } else if (this.deleteBtn.getToggle()) {
                ConfigManager.remove(item);
                this.commandPanel.layout();
            } else {
                item.getRaw().forEach(CommandButtons::send);
            }
        });
    }

    private void addCallBack() {
        CommandEditGUI gui = new CommandEditGUI(new CommandItem("", Lists.newArrayList()));
        Minecraft.getInstance().setScreen(new WrapperCommandGUIScreen(gui, gui::saveData));
    }

    private void editBtnCallback(@NotNull Boolean aBoolean) {
        if (aBoolean) {
            this.deleteBtn.setToggle(false);
        }
    }

    private void deleteBtnCallback(@NotNull Boolean aBoolean) {
        if (aBoolean) {
            this.editBtn.setToggle(false);
        }
    }

    @Override
    public void addPainters() {
        super.addPainters();
        this.rootPanel.setBackgroundPainter(BackgroundPainter.createColorful(0x4D000000));
    }
}
