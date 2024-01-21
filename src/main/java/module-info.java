module tagger {
    requires javafx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome;
    requires java.desktop;
    opens de.b4.tagger.ui;
    exports de.b4.tagger;
}