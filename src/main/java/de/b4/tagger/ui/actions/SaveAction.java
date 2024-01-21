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

public class SaveAction extends Action {
    public SaveAction() {
        this.menuItem = new MenuItem(I18n.getMessage("menu.save.label"));
        this.menuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        this.menuItem.setGraphic(new FontIcon(getIconCode("fa-save")));
        this.menuItem.setOnAction(this::action);
        this.toolbarButton = new Button();
        this.toolbarButton.setGraphic(new FontIcon(getIconCode("fa-save")));
        this.toolbarButton.setOnAction(this::action);
        this.toolbarButton.setTooltip(new Tooltip(I18n.getMessage("menu.save.tooltip")));
    }
    private void action(ActionEvent actionEvent) {
        System.out.println("SAVE...");
    }
}
