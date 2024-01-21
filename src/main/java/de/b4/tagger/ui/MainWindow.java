package de.b4.tagger.ui;

import de.b4.tagger.ui.actions.Action;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Map;

public class MainWindow {
    public static Pane create() {
        return new VBox(
                MainMenu.create(),
                createToolbar(),
                createMainView());
    }

    private static ToolBar createToolbar() {
        Map<String, Action> handler = MainMenu.getMenuItems();
        return new ToolBar(
                handler.get(MainMenu.SAVE).getToolbarButton(),
                new Separator(),
                handler.get(MainMenu.UNDO).getToolbarButton(),
                handler.get(MainMenu.REDO).getToolbarButton(),
                new Separator(),
                handler.get(MainMenu.COPY).getToolbarButton(),
                handler.get(MainMenu.CUT).getToolbarButton(),
                handler.get(MainMenu.PASTE).getToolbarButton()
        );
    }

    private static Pane createMainView() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        return new StackPane(l);
    }
}
