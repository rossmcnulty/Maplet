package net.apunch.maplet.api.menu;

import net.apunch.maplet.Maplet;
import net.apunch.maplet.api.MapColor;
import net.apunch.maplet.api.application.Application;
import net.apunch.maplet.api.attachment.ApplicationIcon;

public final class MainMenu extends Menu {

    public MainMenu() {
        super("Maplet");

        int x = 5;
        int y = 35;
        for (Application application : Maplet.getApplications()) {
            addAttachment(new ApplicationIcon(application.getName(), x, y, 25, 35, MapColor.WHITE, application.getIcon()));

            x += 30;
            if (x >= 128 - 30) {
                // Move to new row
                x = 5;
                y = y + 40;
            }

            if (y >= 128 - 25) {
                break; // TODO: Break for now, add new pages later
            }
        }
    }
}
