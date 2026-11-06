package work.msdnicrosoft.commandbuttons.gui;

import com.google.common.collect.Lists;
import io.github.cottonmc.cotton.gui.widget.WListPanel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import work.msdnicrosoft.commandbuttons.data.CommandItem;
import work.msdnicrosoft.commandbuttons.mixin.IWListPanel;
import work.msdnicrosoft.commandbuttons.mixin.IWWidget;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CommandListPanel<D, W extends WWidget> extends WListPanel<D, W> {
    private final WTextField search;

    public CommandListPanel(List<D> data, Supplier<W> supplier, BiConsumer<D, W> configurator, WTextField search) {
        super(data, supplier, configurator);
        this.search = search;
    }

    private List<D> getVisibleData() {
        List<D> visibleData = Lists.newArrayList(this.data);
        if (!this.search.getText().isEmpty()) {
            visibleData = visibleData.stream()
                    .filter(d -> ((CommandItem) d).getDisplayName().contains(this.search.getText().trim()))
                    .collect(Collectors.toList());
        }
        return visibleData;
    }

    /**
     * Moves an item to the grid cell currently underneath the mouse pointer.
     * The backing list is the same list ConfigManager serializes, so its order is persistent.
     */
    public void move(D dragged, int mouseX, int mouseY) {
        List<D> visibleData = this.getVisibleData();
        if (visibleData.size() < 2 || !visibleData.contains(dragged)) {
            return;
        }

        int column = Math.max(0, Math.min(mouseX / 83, 3));
        int row = Math.max(mouseY / 22, 0);
        int visibleIndex = this.scrollBar.getValue() + row * 4 + column;
        visibleIndex = Math.min(visibleIndex, visibleData.size() - 1);

        D target = visibleData.get(visibleIndex);
        if (dragged == target) {
            return;
        }

        int targetIndex = this.data.indexOf(target);
        if (targetIndex < 0 || !this.data.remove(dragged)) {
            return;
        }
        this.data.add(Math.min(targetIndex, this.data.size()), dragged);
        this.layout();
    }

    /**
     * Modified from LibGui
     */
    @SuppressWarnings("unchecked")
    @Override
    public void layout() {
        this.children.clear();
        this.children.add(scrollBar);
        scrollBar.setLocation(this.width - scrollBar.getWidth(), 0);
        scrollBar.setSize(8, this.height);

        if (!fixedHeight) {
            if (unconfigured.isEmpty()) {
                if (configured.isEmpty()) {
                    W exemplar = ((IWListPanel<W>) this).invokeCreateChild();
                    unconfigured.add(exemplar);
                    if (!exemplar.canResize()) cellHeight = exemplar.getHeight();
                } else {
                    W exemplar = configured.values().iterator().next();
                    if (!exemplar.canResize()) cellHeight = exemplar.getHeight();
                }
            } else {
                W exemplar = unconfigured.get(0);
                if (!exemplar.canResize()) cellHeight = exemplar.getHeight();
            }
        }
        if (cellHeight < 4) cellHeight = 4;

        int layoutHeight = this.getHeight() - 4;
        int cellsHigh = Math.max((layoutHeight + 2) / (cellHeight + 2), 1);

        List<D> data = this.getVisibleData();

        scrollBar.setWindow(cellsHigh);
        scrollBar.setMaxValue(data.size() > 32 ? data.size() - 8 : 8);
        int scrollOffset = scrollBar.getValue();

        int presentCells = Math.min(data.size() - scrollOffset / 4 + 1, 32);

        int offsetX = 0;
        int offsetY = 0;

        if (presentCells > 0) {
            for (int i = 0; i < presentCells; i++) {
                int index = i + scrollOffset;
                if (index >= data.size()) break;
                if (index < 0) continue;
                D d = data.get(index);
                W w = configured.get(d);
                if (w == null) {
                    if (unconfigured.isEmpty()) {
                        w = ((IWListPanel<W>) this).invokeCreateChild();
                    } else {
                        w = unconfigured.remove(0);
                    }
                    configured.put(d, w);
                }
                configurator.accept(d, w);

                ((IWWidget) w).setX((w.getWidth() + 5) * offsetX);
                ((IWWidget) w).setY(offsetY * 22);
                offsetX++;

                if (offsetX >= 4) {
                    offsetX = 0;
                    offsetY++;
                }

                this.children.add(w);
            }
        }
    }
}
