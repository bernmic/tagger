package de.b4.tagger;

import de.b4.tagger.ui.MainWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    private static Configuration configuration;
    StackPane root;

    @Override
    public void init() {
        System.out.println("Init...");
        Main.configuration = Configuration.newInstance();
        System.out.println("Java   version: " + System.getProperty("java.version"));
        System.out.println("JavaFX version: " + System.getProperty("javafx.version"));
        System.out.println("JavaFX Runtime: " + System.getProperty("javafx.runtime.version"));
    }

    @Override
    public void stop() {
        configuration.setWidth((int) root.getWidth());
        configuration.setHeight((int) root.getHeight());
        configuration.save();
        System.out.println("Stop...");
    }

    @Override
    public void start(Stage stage) {
        root = new StackPane(MainWindow.create());
        Scene mainWindow = new Scene(root, configuration.getWidth(), configuration.getHeight());
        stage.setScene(mainWindow);
        stage.setTitle(Constants.APP_NAME);
        stage.show();
    }

    public static Configuration getConfiguration() {
        return configuration;
    }

    public static void main(String[] args) {
        launch();
    }

}