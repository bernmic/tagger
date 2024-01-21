package de.b4.tagger.ui.actions;

import de.b4.tagger.ui.I18n;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

public class QuitAction extends Action {
    public QuitAction() {
        this.menuItem = new MenuItem(I18n.getMessage("menu.quit.label"));
        this.menuItem.setOnAction(this::action);
    }
    private void action(ActionEvent actionEvent) {
        Platform.exit();
    }
}
