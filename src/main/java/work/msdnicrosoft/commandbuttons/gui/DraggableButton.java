package work.msdnicrosoft.commandbuttons.gui;

import io.github.cottonmc.cotton.gui.widget.WButton;
//#if MC >= 12109
import net.minecraft.client.input.MouseButtonEvent;
//#endif
//#if MC > 11605
import io.github.cottonmc.cotton.gui.widget.data.InputResult;
//#else
//$$ import io.github.cottonmc.cotton.gui.widget.WWidget;
//#endif

import java.util.function.BiConsumer;

/**
 * A regular LibGui button that also reports intentional drag gestures.
 * Small mouse movements are kept as normal button clicks.
 */
public class DraggableButton extends WButton {
    private static final double DRAG_THRESHOLD = 3.0D;

    private BiConsumer<Integer, Integer> onDrag;
    private Runnable onDragEnd;
    private double draggedX;
    private double draggedY;
    private boolean dragging;
    private boolean suppressNextClick;

    public DraggableButton setOnDrag(BiConsumer<Integer, Integer> onDrag) {
        this.onDrag = onDrag;
        return this;
    }

    public DraggableButton setOnDragEnd(Runnable onDragEnd) {
        this.onDragEnd = onDragEnd;
        return this;
    }

    private void beginPointerInteraction() {
        this.draggedX = 0.0D;
        this.draggedY = 0.0D;
        this.dragging = false;
        this.suppressNextClick = false;
    }

    private void drag(int x, int y, double deltaX, double deltaY) {
        this.draggedX += deltaX;
        this.draggedY += deltaY;
        if (!this.dragging && this.draggedX * this.draggedX + this.draggedY * this.draggedY
                >= DRAG_THRESHOLD * DRAG_THRESHOLD) {
            this.dragging = true;
        }
        if (this.dragging && this.onDrag != null) {
            this.onDrag.accept(x, y);
        }
    }

    private void endPointerInteraction() {
        this.suppressNextClick = this.dragging;
        if (this.dragging && this.onDragEnd != null) {
            this.onDragEnd.run();
        }
        this.dragging = false;
    }

    //#if MC >= 12109
    @Override
    public InputResult onMouseDown(MouseButtonEvent click, boolean doubled) {
        this.beginPointerInteraction();
        return super.onMouseDown(click, doubled);
    }

    @Override
    public InputResult onMouseDrag(MouseButtonEvent click, double offsetX, double offsetY) {
        this.drag((int) click.x(), (int) click.y(), offsetX, offsetY);
        return this.dragging ? InputResult.PROCESSED : super.onMouseDrag(click, offsetX, offsetY);
    }

    @Override
    public InputResult onMouseUp(MouseButtonEvent click) {
        this.endPointerInteraction();
        return super.onMouseUp(click);
    }

    @Override
    public InputResult onClick(MouseButtonEvent click, boolean doubled) {
        if (this.suppressNextClick) {
            this.suppressNextClick = false;
            return InputResult.PROCESSED;
        }
        return super.onClick(click, doubled);
    }
    //#elseif MC > 11605
    //$$ @Override
    //$$ public InputResult onMouseDown(int x, int y, int button) {
    //$$     this.beginPointerInteraction();
    //$$     return super.onMouseDown(x, y, button);
    //$$ }
    //$$
    //$$ @Override
    //$$ public InputResult onMouseDrag(int x, int y, int button, double deltaX, double deltaY) {
    //$$     this.drag(x, y, deltaX, deltaY);
    //$$     return this.dragging ? InputResult.PROCESSED : super.onMouseDrag(x, y, button, deltaX, deltaY);
    //$$ }
    //$$
    //$$ @Override
    //$$ public InputResult onMouseUp(int x, int y, int button) {
    //$$     this.endPointerInteraction();
    //$$     return super.onMouseUp(x, y, button);
    //$$ }
    //$$
    //$$ @Override
    //$$ public InputResult onClick(int x, int y, int button) {
    //$$     if (this.suppressNextClick) {
    //$$         this.suppressNextClick = false;
    //$$         return InputResult.PROCESSED;
    //$$     }
    //$$     return super.onClick(x, y, button);
    //$$ }
    //#else
    //$$ @Override
    //$$ public WWidget onMouseDown(int x, int y, int button) {
    //$$     this.beginPointerInteraction();
    //$$     return super.onMouseDown(x, y, button);
    //$$ }
    //$$
    //$$ @Override
    //$$ public void onMouseDrag(int x, int y, int button, double deltaX, double deltaY) {
    //$$     this.drag(x, y, deltaX, deltaY);
    //$$ }
    //$$
    //$$ @Override
    //$$ public WWidget onMouseUp(int x, int y, int button) {
    //$$     this.endPointerInteraction();
    //$$     return super.onMouseUp(x, y, button);
    //$$ }
    //$$
    //$$ @Override
    //$$ public void onClick(int x, int y, int button) {
    //$$     if (this.suppressNextClick) {
    //$$         this.suppressNextClick = false;
    //$$         return;
    //$$     }
    //$$     super.onClick(x, y, button);
    //$$ }
    //#endif
}
