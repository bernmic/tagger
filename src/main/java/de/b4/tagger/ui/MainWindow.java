package de.b4.tagger.ui;

import de.b4.tagger.ui.actions.Action;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.*;

import java.util.Map;

public class MainWindow {
    public static DirectoryTree directoryTree;
    public static DataTable dataTable;

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
        SplitPane mainArea = new SplitPane();
        directoryTree = DirectoryTree.create();
        dataTable = DataTable.create();
        dataTable.connectTree(directoryTree);
        mainArea.getItems().addAll(directoryTree, dataTable);
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
