package de.b4.tagger.ui;

import de.b4.tagger.Main;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import javax.swing.filechooser.FileSystemView;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DirectoryTree extends TreeView<DirectoryTree.Directory> implements ObservableValue<DirectoryTree.Directory> {
    private Set<InvalidationListener> invalidationListener = new HashSet<>();
    private Set<ChangeListener<? super Directory>> selectionChangeListener = new HashSet<>();
    private Directory currentSelected = null;

    private DirectoryTree() {}
    private DirectoryTree(TreeItem<Directory> treeItem) {
        super(treeItem);
    }

    public static DirectoryTree create() {
        DirectoryTree treeView = new DirectoryTree(new TreeItem<>(new Directory(null, "root")));
        treeView.setShowRoot(false);
        for (File f : File.listRoots()) {
            String name = FileSystemView.getFileSystemView().getSystemDisplayName(f);
            if (name == null || "".equals(name)) {
                name = f.getAbsolutePath();
            }
            DirectoryTreeItem item = new DirectoryTreeItem(new Directory(f, name));
            treeView.getRoot().getChildren().add(item);
        }
        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Directory>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<Directory>> observableValue, TreeItem<Directory> oldItem, TreeItem<Directory> newItem) {
                if (newItem != null) {
                    for (InvalidationListener i : treeView.invalidationListener) {
                        i.invalidated(treeView);
                    }
                    for (ChangeListener<? super Directory> c : treeView.selectionChangeListener) {
                        c.changed(treeView, treeView.currentSelected, newItem.getValue());
                    }
                    treeView.currentSelected = newItem.getValue();
                    Main.getConfiguration().setCurrentPath(treeView.currentSelected.getFile().getAbsolutePath());
                } else {
                    treeView.currentSelected = null;
                    Main.getConfiguration().setCurrentPath("");
                }
            }
        });
        return treeView;
    }

    @Override
    public void addListener(ChangeListener<? super Directory> listener) {
        this.selectionChangeListener.add(listener);
    }

    @Override
    public void removeListener(ChangeListener<? super Directory> listener) {
        this.selectionChangeListener.remove(listener);
    }

    @Override
    public Directory getValue() {
        return currentSelected;
    }

    @Override
    public void addListener(InvalidationListener listener) {
        this.invalidationListener.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        this.invalidationListener.remove(listener);
    }

    public void expandToPath(String path) {
        expandPath(this.getRoot(), path);
    }

    private void expandPath(TreeItem<Directory> item, String path) {
        for (TreeItem<Directory> child : item.getChildren()) {
            String p = child.getValue().getFile().getAbsolutePath();
            if (p.equals(path)) {
                this.getSelectionModel().select(child);
                return;
            }
            if (path.startsWith(p)) {
                child.setExpanded(true);
                expandPath(child, path);
            }
        }
    }

    public static class Directory {
        private File file;
        private String name;

        public Directory(File file, String name) {
            this.file = file;
            this.name = name;
        }

        public File getFile() {
            return file;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static class DirectoryTreeItem extends TreeItem<Directory> {
        private boolean firstTimeChildren = true;
        private boolean hasChildren = true;

        public DirectoryTreeItem(Directory directory) {
            super(directory);
        }

        @Override
        public ObservableList<TreeItem<Directory>> getChildren() {
            if (firstTimeChildren) {
                firstTimeChildren = false;
                super.getChildren().addAll(buildChildrenList());
            }
            return super.getChildren();
        }

        private ObservableList<TreeItem<Directory>> buildChildrenList() {
            ObservableList<TreeItem<Directory>> children = FXCollections.observableArrayList();
            if (this.getValue() != null && this.getValue().getFile() != null) {
                File[] files = this.getValue().getFile().listFiles();
                if (files != null) {
                    Arrays.sort(files);
                    for (File f : files) {
                        if (f.isDirectory() && !f.isHidden()) {
                            children.add(new DirectoryTreeItem(new Directory(f, f.getName())));
                        }
                    }
                }
            }
            if (children.isEmpty()) {
                hasChildren = false;
            }
            return children;
        }

        @Override
        public boolean isLeaf() {
            return !hasChildren;
        }
    }
}
