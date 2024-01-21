package de.b4.tagger.ui;

import de.b4.tagger.ui.actions.Action;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.*;

import java.util.Map;

public class MainWindow {
    public static Pane create() {
        return new VBox(
                MainMenu.create(),
                createToolbar(),
                createMainView(),
                createStatusbar());
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

    private static SplitPane createMainView() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");

        SplitPane mainArea = new SplitPane();
        mainArea.getItems().addAll(DirectoryTree.create(), new VBox(l));
        VBox.setVgrow(mainArea, Priority.ALWAYS);
        mainArea.setDividerPosition(0, 0.33);

        return mainArea;
    }

    private static Pane createStatusbar() {
        HBox statusBar = new HBox();
        statusBar.getChildren().addAll(new Label(I18n.getMessage("status.ready.text")));
        return statusBar;
    }
}
