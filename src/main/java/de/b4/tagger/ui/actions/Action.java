package de.b4.tagger.ui.actions;

import de.b4.tagger.Main;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

public abstract class Action {
    protected MenuItem menuItem;
    protected Button toolbarButton;
    public MenuItem getMenuItem() {
        return menuItem;
    }

    public Button getToolbarButton() {
        return toolbarButton;
    }

    String getIconCode(String code) {
        return Main.getConfiguration().getIconCode(code);
    }
}
