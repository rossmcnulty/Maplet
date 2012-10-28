package net.apunch.maplet.api.attachment;

import net.apunch.maplet.api.Map;
import net.apunch.maplet.api.menu.Menu;

public class BackButton extends Button {

    public BackButton(int x, int y) {
        super(x, y, "<--");
    }

    @Override
    public void onClick(Map map) {
        Menu previous = map.getActiveMenu().getParent();
        if (previous != null) {
            map.setActiveMenu(previous);
        }
    }
}
