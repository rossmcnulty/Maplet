package net.apunch.maplet.api.menu;

import net.apunch.maplet.api.Map;
import net.apunch.maplet.api.MapColor;
import net.apunch.maplet.api.attachment.Attachment;
import net.apunch.maplet.api.attachment.Label;
import net.apunch.maplet.api.attachment.SelectableAttachment;

/**
 * Represents a menu that is displayed on a map.
 */
public abstract class Menu extends Attachment {
    private Menu parent;
    private int selectedIndex = -1;
    private SelectableAttachment selected;

    /**
     * Constructs a menu with the given title.
     * 
     * @param title
     *            Title to assign to this menu
     */
    public Menu(String title) {
        addAttachment(new Label(title, 50 - title.length(), 5, MapColor.DARK_GRAY));
    }

    public final void cycleSelected(Map map, int direction) {
        if (selectables.size() > 0) {
            if (selected == null) {
                selected = selectables.get(0);
            } else {
                // Deselect the old selected attachment
                selected.setSelected(false);
                selected.onDeselect(map);
                if (selectables.size() == 1) {
                    selected = null;
                    return;
                } else {
                    selectedIndex = selectedIndex + direction;
                    if (selectedIndex == selectables.size()) {
                        selectedIndex = 0;
                    } else if (selectedIndex < 0) {
                        selectedIndex = selectables.size() - 1;
                    }

                    // Set the new selected attachment
                    selected = selectables.get(selectedIndex);
                }
            }

            setSelectedAttachment(map, selected);
        }
    }

    /**
     * Gets the menu that is directly above this menu on a map's hierarchy of menus.
     * 
     * @return This menu's parent menu, or null if there is no parent.
     */
    public final Menu getParent() {
        return parent;
    }

    /**
     * Gets the selected attachment on this menu.
     * 
     * @return Selected attachment on this menu, or null if no attachment is selected.
     */
    public final SelectableAttachment getSelectedAttachment() {
        return selected;
    }

    /**
     * Sets thie menu's parent menu.
     * 
     * @param menu
     *            Menu to set as parent
     */
    public final void setParent(Menu menu) {
        parent = menu;
    }

    /**
     * Sets this menu's current selected attachment.
     * 
     * @param map
     *            Map containing the given attachment
     * @param attachment
     *            Attachment to set as selected
     */
    public final void setSelectedAttachment(Map map, SelectableAttachment attachment) {
        attachment.onSelect(map);
        attachment.setSelected(true);
    }
}
