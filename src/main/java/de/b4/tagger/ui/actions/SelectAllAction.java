package de.b4.tagger.ui.actions;

import de.b4.tagger.ui.I18n;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.kordamp.ikonli.javafx.FontIcon;

public class SelectAllAction extends Action {
    public SelectAllAction() {
        this.menuItem = new MenuItem(I18n.getMessage("menu.selectall.label"));
        this.menuItem.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        this.menuItem.setOnAction(this::action);
    }
    private void action(ActionEvent actionEvent) {
        System.out.println("SELECTALL...");
    }
}
