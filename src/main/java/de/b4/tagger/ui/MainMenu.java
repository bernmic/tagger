package de.b4.tagger.ui;

import de.b4.tagger.ui.actions.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SeparatorMenuItem;

import java.util.HashMap;
import java.util.Map;

public class MainMenu {
    private final static Map<String, Action> menuItems = new HashMap<>();
    public final static String FILE = "menu.file.label";
    public final static String EDIT = "menu.edit.label";
    public final static String SAVE = "menu.save.label";
    public final static String QUIT = "menu.quit.label";
    public final static String UNDO = "menu.undo.label";
    public final static String REDO = "menu.redo.label";
    public final static String COPY = "menu.copy.label";
    public final static String CUT = "menu.cut.label";
    public final static String PASTE = "menu.paste.label";
    public final static String SELECTALL = "menu.select.label";

    public static Map<String,Action> getMenuItems() {
        return menuItems;
    }

    public static MenuBar create() {
        MenuBar m = new MenuBar();
        m.getMenus().add(fileMenu());
        m.getMenus().add(editMenu());
        return m;
    }

    private static Menu fileMenu() {
        Action save = new SaveAction();
        Action quit = new QuitAction();
        Menu file = new Menu(I18n.getMessage(FILE));
        file.getItems().addAll(
                save.getMenuItem(),
                new SeparatorMenuItem(),
                quit.getMenuItem()
        );
        menuItems.put(SAVE, save);
        menuItems.put(QUIT, quit);
        return file;
    }

    private static Menu editMenu() {
        Action undo = new UndoAction();
        Action redo = new RedoAction();
        Action copy = new CopyAction();
        Action cut = new CutAction();
        Action paste = new PasteAction();
        Action selectAll = new SelectAllAction();
        Menu edit = new Menu(I18n.getMessage(EDIT));
        edit.getItems().addAll(
                undo.getMenuItem(),
                redo.getMenuItem(),
                new SeparatorMenuItem(),
                copy.getMenuItem(),
                cut.getMenuItem(),
                paste.getMenuItem(),
                new SeparatorMenuItem(),
                selectAll.getMenuItem()
        );
        menuItems.put(UNDO, undo);
        menuItems.put(REDO, redo);
        menuItems.put(COPY, copy);
        menuItems.put(CUT, cut);
        menuItems.put(PASTE, paste);
        return edit;
    }
}
