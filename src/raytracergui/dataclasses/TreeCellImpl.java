package raytracergui.dataclasses;

import javafx.scene.control.cell.CheckBoxTreeCell;
import raytracergui.container.NodeContainer;

/**
 * Created by jroehl on 21.01.16.
 */
public class TreeCellImpl extends CheckBoxTreeCell<NodeContainer> {

    public TreeCellImpl() {

    }

    @Override
    public void updateItem(NodeContainer item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
        }
    }

}
