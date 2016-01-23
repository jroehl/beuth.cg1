package raytracergui.dataclasses;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.WeakListChangeListener;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import org.controlsfx.control.CheckTreeView;
import raytracergui.container.NodeContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jroehl on 21.01.16.
 */
public class CheckTreeViewWithItems<T extends HierarchyData<T>> extends CheckTreeView {

    /**
     * Keep hard references for each listener, so that they don't get garbage collected too soon.
     */
    private final Map<CheckBoxTreeItem<T>, ListChangeListener<T>> hardReferences = new HashMap<>();

    /**
     * Also store a reference from each tree item to its weak listeners, so that the listener can be removed, when the tree item gets removed.
     */
    private final Map<CheckBoxTreeItem<T>, WeakListChangeListener<T>> weakListeners = new HashMap<>();

    private ObjectProperty<ObservableList<? extends T>> items = new SimpleObjectProperty<>(this, "items");

    public ObservableList<NodeContainer> allEntries = FXCollections.observableArrayList();

    public CheckTreeViewWithItems() {
        super();
        init();
    }

    /**
     * Creates the tree view.
     *
     * @param root The root tree item.
     * @see CheckTreeView#(javafx.scene.control.TreeItem)
     */
    public CheckTreeViewWithItems(CheckBoxTreeItem<T> root) {
        super(root);
        init();
    }

    /**
     * Initializes the tree view.
     */
    private void init() {
        rootProperty().addListener(new ChangeListener<CheckBoxTreeItem<T>>() {
            @Override
            public void changed(ObservableValue<? extends CheckBoxTreeItem<T>> observableValue, CheckBoxTreeItem<T> oldRoot, CheckBoxTreeItem<T> newRoot) {
                clear(oldRoot);
                updateItems();
            }
        });

        setItems(FXCollections.<T>observableArrayList());

        // Do not use ChangeListener, because it won't trigger if old list equals new list (but in fact different references).
        items.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                clear((CheckBoxTreeItem<T>) getRoot());
                updateItems();
            }
        });
    }

    /**
     * Removes all listener from a root.
     *
     * @param root The root.
     */
    private void clear(CheckBoxTreeItem<T> root) {
        if (root != null) {
            for (TreeItem<T> treeItem : root.getChildren()) {
                removeRecursively((CheckBoxTreeItem<T>) treeItem);
            }

            removeRecursively(root);
            root.getChildren().clear();
        }
    }

    /**
     * Updates the items.
     */
    private void updateItems() {

        if (getItems() != null) {
            for (T value : getItems()) {
                getRoot().getChildren().add(addRecursively(value));
            }

            ListChangeListener<T> rootListener = getListChangeListener(getRoot().getChildren());
            WeakListChangeListener<T> weakListChangeListener = new WeakListChangeListener<T>(rootListener);
            hardReferences.put((CheckBoxTreeItem<T>) getRoot(), rootListener);
            weakListeners.put((CheckBoxTreeItem<T>) getRoot(), weakListChangeListener);
            getItems().addListener(weakListChangeListener);
        }
    }

    /**
     * Gets a {@link javafx.collections.ListChangeListener} for a  {@link TreeItem}. It listens to changes on the underlying list and updates the UI accordingly.
     *
     * @param treeItemChildren The associated tree item's children list.
     * @return The listener.
     */
    private ListChangeListener<T> getListChangeListener(final ObservableList<CheckBoxTreeItem<T>> treeItemChildren) {
        return change -> {
            while (change.next()) {
                if (change.wasUpdated()) {
                    // http://javafx-jira.kenai.com/browse/RT-23434
                    continue;
                }
                if (change.wasRemoved()) {
                    for (int i = change.getRemovedSize() - 1; i >= 0; i--) {
                        removeRecursively(treeItemChildren.remove(change.getFrom() + i));
                    }
                }
                // If items have been added
                if (change.wasAdded()) {
                    // Get the new items
                    for (int i = change.getFrom(); i < change.getTo(); i++) {
                        treeItemChildren.add(i, addRecursively(change.getList().get(i)));
                    }
                }
                // If the list was sorted.
                if (change.wasPermutated()) {
                    // Store the new order.
                    Map<Integer, CheckBoxTreeItem<T>> tempMap = new HashMap<Integer, CheckBoxTreeItem<T>>();

                    for (int i = change.getTo() - 1; i >= change.getFrom(); i--) {
                        int a = change.getPermutation(i);
                        tempMap.put(a, treeItemChildren.remove(i));
                    }

                    getSelectionModel().clearSelection();

                    // Add the items in the new order.
                    for (int i = change.getFrom(); i < change.getTo(); i++) {
                        treeItemChildren.add(tempMap.remove(i));
                    }
                }
            }
        };
    }

    /**
     * Removes the listener recursively.
     *
     * @param item The tree item.
     */
    private CheckBoxTreeItem<T> removeRecursively(CheckBoxTreeItem<T> item) {
        if (item.getValue() != null && item.getValue().getChildren() != null) {

            if (weakListeners.containsKey(item)) {
                item.getValue().getChildren().removeListener(weakListeners.remove(item));
                hardReferences.remove(item);
            }
            for (TreeItem<T> treeItem : item.getChildren()) {
                removeRecursively((CheckBoxTreeItem<T>) treeItem);
            }
        }
        return item;
    }

    /**
     * Adds the children to the tree recursively.
     *
     * @param value The initial value.
     * @return The tree item.
     */
    private CheckBoxTreeItem<T> addRecursively(T value) {

        CheckBoxTreeItem<T> treeItem = new CheckBoxTreeItem<T>();
        treeItem.setValue(value);
        treeItem.setExpanded(true);

        if (value != null && value.getChildren() != null) {
            ObservableList<TreeItem<T>> temp = treeItem.getChildren();
            ObservableList<CheckBoxTreeItem<T>> tempChanged = FXCollections.observableArrayList();
            for (TreeItem t : temp) {
                tempChanged.add((CheckBoxTreeItem<T>) t);
            }
            ListChangeListener<T> listChangeListener = getListChangeListener(tempChanged);
            WeakListChangeListener<T> weakListener = new WeakListChangeListener<T>(listChangeListener);
            value.getChildren().addListener(weakListener);

            hardReferences.put(treeItem, listChangeListener);
            weakListeners.put(treeItem, weakListener);
            for (T child : value.getChildren()) {
                treeItem.getChildren().add(addRecursively(child));
            }
        }
        return treeItem;
    }

    public ObservableList<? extends T> getItems() {
        return items.get();
    }

    /**
     * Sets items for the tree.
     *
     * @param items The list.
     */
    public void setItems(ObservableList<? extends T> items) {
        this.items.set(items);
    }

    public void refreshList(CheckBoxTreeItem<NodeContainer> root) {
        if (root.getValue() != null)
            allEntries.add(root.getValue());
        for (TreeItem<NodeContainer> child : root.getChildren()) {
            if (child.getChildren().isEmpty()) {
                allEntries.add(child.getValue());
            } else {
                refreshList((CheckBoxTreeItem<NodeContainer>) child);
            }
        }
    }
}
