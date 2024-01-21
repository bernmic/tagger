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

public class CopyAction extends Action {
    public CopyAction() {
        this.menuItem = new MenuItem(I18n.getMessage("menu.copy.label"));
        this.menuItem.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        this.menuItem.setGraphic(new FontIcon(getIconCode("fa-copy")));
        this.menuItem.setOnAction(this::action);
        this.toolbarButton = new Button();
        this.toolbarButton.setGraphic(new FontIcon(getIconCode("fa-copy")));
        this.toolbarButton.setOnAction(this::action);
        this.toolbarButton.setTooltip(new Tooltip(I18n.getMessage("menu.copy.tooltip")));
    }
    private void action(ActionEvent actionEvent) {
        System.out.println("COPY...");
    }
}
