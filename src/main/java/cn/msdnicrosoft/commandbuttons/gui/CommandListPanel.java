package cn.msdnicrosoft.commandbuttons.gui;

import cn.msdnicrosoft.commandbuttons.data.CommandItem;
import cn.msdnicrosoft.commandbuttons.mixin.IWListPanel;
import cn.msdnicrosoft.commandbuttons.mixin.IWWidget;
import com.google.common.collect.Lists;
import io.github.cottonmc.cotton.gui.widget.WListPanel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.WWidget;

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

    /*
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

        List<D> data = Lists.newArrayList(this.data);
        if (!this.search.getText().isEmpty()) {
            data = data.stream().filter(d -> ((CommandItem) d).getDisplayName().contains(this.search.getText().trim())).collect(Collectors.toList());
        }

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
                    configurator.accept(d, w);
                    configured.put(d, w);
                }

                ((IWWidget) w).setX(10 + (w.getWidth() + 3) * offsetX);
                ((IWWidget) w).setY(5 + offsetY * 22);
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
