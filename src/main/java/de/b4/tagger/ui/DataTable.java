package de.b4.tagger.ui;

import de.b4.tagger.Main;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DataTable extends TableView<DataTable.Song> {
    private DirectoryTree.Directory currentDirectory;

    private DataTable() {}

    public static DataTable create() {
        DataTable t = new DataTable();
        t.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        t.getColumns().addAll(
                t.createTableColumn(I18n.getMessage("column.filename.label"), "filename", 15)
        );
        return t;
    }

    private TableColumn<Song, String> createTableColumn(String title, String property, int width) {
        TableColumn<Song, String> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        column.setMaxWidth(1f * Integer.MAX_VALUE * width);
        return column;
    }

    public void connectTree(DirectoryTree tree) {
        tree.addListener((observableValue, directory, t1) -> {
            currentDirectory = t1;

            //ProgressIndicator pi = new ProgressIndicator();
            Label title = new Label("");
            ProgressBar pb = new ProgressBar();
            Label status = new Label("");
            status.setMinWidth(300);
            VBox box = new VBox(title, pb, status);
            box.setAlignment(Pos.CENTER);

            Main.showOverlay(box);
            SelectionChangedService service = new SelectionChangedService();
            service.setDirectory(currentDirectory);
            service.setOnSucceeded(e -> {
                List<Song> songs = (List<Song>) e.getSource().getValue();
                getItems().clear();
                getItems().addAll(songs);
                Main.hideOverlay(box);
            });
            title.textProperty().bind(service.titleProperty());
            pb.progressProperty().bind(service.progressProperty());
            status.textProperty().bind(service.messageProperty());
            service.start();
        });
    }

    private static class SelectionChangedService extends Service<List<Song>> {
        private DirectoryTree.Directory directory;

        public void setDirectory(DirectoryTree.Directory directory) {
            this.directory = directory;
        }

        @Override
        protected Task<List<Song>> createTask() {
            return new Task<>() {
                @Override
                protected List<Song> call() throws Exception {
                    updateTitle(I18n.getMessage("loading.label") + " " + directory.getName());
                    List<Song> songs = new ArrayList<>();
                    if (directory != null && directory.getFile() != null) {
                        File[] files = getSongFiles(); // directory.getFile().listFiles();
                        if (files != null) {
                            Arrays.sort(files);
                            int i = 0, count = files.length;
                            for (File f : files) {
                                updateMessage(f.getName());
                                updateProgress(i, count);
                                i++;
                                if (f.isFile() && f.getName().toLowerCase().endsWith(".mp3")) {
                                    songs.add(new Song(f));
                                }
                            }
                        }
                    }
                    return songs;
                }
            };
        }
        private File[] getSongFiles() throws IOException {
            if (Main.getConfiguration().isReadRecursive()) {
                List<File> files = new ArrayList<>();
                Files.find(Paths.get(directory.getFile().getAbsolutePath()), 2, (filePath, fileAttr) -> {
                    return fileAttr.isRegularFile() && filePath.toString().toLowerCase().endsWith(".mp3");
                }).forEach(p -> files.add(p.toFile()));
                Collections.sort(files);
                return files.toArray(new File[0]);
            }
            else {
                return directory.getFile().listFiles();
            }
        }
    }

    public static class Song {
        private String filename;
        private String path;
        private String fileType;
        private boolean dirty;
        private File file;

        private Song() {}
        public Song(File file) {
            this.dirty = false;
            this.file = file;
            this.filename = this.file.getName();
            this.path = file.getAbsolutePath().substring(0, file.getAbsolutePath().indexOf(file.getName()));
            setFileType();
        }
        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            if (!filename.equals(this.filename)) {
                dirty = true;
            }
            this.filename = filename;
            setFileType();
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getFileType() {
            return fileType;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }

        public boolean isDirty() {
            return dirty;
        }

        public void setDirty(boolean dirty) {
            this.dirty = dirty;
        }

        private void setFileType() {
            if (filename.toLowerCase().endsWith(".mp3")) {
                fileType = "mp3";
            } else if (filename.toLowerCase().endsWith(".mp4")) {
                fileType = "mp4";
            } else if (filename.toLowerCase().endsWith(".mpc")) {
                fileType = "mpc";
            } else if (filename.toLowerCase().endsWith(".asf")) {
                fileType = "asf";
            } else if (filename.toLowerCase().endsWith(".flac")) {
                fileType = "flac";
            } else if (filename.toLowerCase().endsWith(".wav")) {
                fileType = "wav";
            } else if (filename.toLowerCase().endsWith(".ogg")) {
                fileType = "ogg";
            } else {
                fileType = "unknown";
            }
        }
    }
}
