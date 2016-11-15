package net.simplewebapps.edcc.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.simplewebapps.edcc.BaseEventSubscriber;
import net.simplewebapps.edcc.util.JavaFxEventSubscriber;
import net.simplewebapps.edcc.JournalReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class RootController implements Initializable {

    private final BaseEventSubscriber subscriber;

    @FXML private VBox mainArea;
    @FXML private VBox cmdrArea;

    @Value("${title.log.filechooser:Open log file}")
    private String fileChooserTitle;

    @FXML
    private Button chooseFileBtn;

    @FXML
    private TextField logfilePathTxt;

    @FXML
    private Label cmdrNameLabel;

    private JournalReader journalReader;

    private Stage callingStage;

    private FileChooser fileChooser;


    @Autowired
    public RootController(JournalReader journalReader, JavaFxEventSubscriber subscriber) {
        this.journalReader = journalReader;
        this.subscriber = subscriber;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chooseFileBtn.setOnMouseClicked(this::onChooseFileClicked);
        configureFileChooser();
    }
    private void configureFileChooser() {
        fileChooser = new FileChooser();
        fileChooser.setTitle(fileChooserTitle);
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Log File", "*.log"));
    }

    private void onChooseFileClicked(MouseEvent event) {

        File candidate = checkForText();
        if (candidate == null) {
            candidate = new File(System.getProperty("user.home"));
        }
        fileChooser.setInitialDirectory(candidate);

        File file = fileChooser.showOpenDialog(callingStage);
        if (file != null) { // null when closed without selecting
            logfilePathTxt.setText(file.getAbsolutePath());
            startWatchdog();
            chooseFileBtn.setDisable(true);
            logfilePathTxt.setDisable(true);
        }
    }

    private void startWatchdog() {
        Thread watchdogThread = new Thread(() -> journalReader.readJournal(logfilePathTxt.getText()));
        watchdogThread.setDaemon(true);
        watchdogThread.start();
    }

    private File checkForText() {
        String path = logfilePathTxt.getText();
        if (path != null) {
            File file = new File(path);
            if (file.exists() && file.canRead()) {
                if (!file.isDirectory()) {
                    return file.getParentFile();
                }
            }
        }

        return null;
    }

    private static String findNewestLogfile() {
        return RootController.class.getClassLoader().getResource("Journal.161025234203.01.log").getPath();
    }

    public void setChooseFileBtn(Button chooseFileBtn) {
        this.chooseFileBtn = chooseFileBtn;
    }

    public void setLogfilePathTxt(TextField logfilePathTxt) {
        this.logfilePathTxt = logfilePathTxt;
    }

    public void setCallingStage(Stage callingStage) {
        this.callingStage = callingStage;
        callingStage.setOnCloseRequest(event -> journalReader.stopWatchdog());
    }

//    @Override
//    public boolean accepts(Class<? extends Event> eventClass) {
//        return LoadGame.class.equals(eventClass);
//    }
//
//    @Override
//    public void onEvent(Event event) {
//        LoadGame loadGame = (LoadGame) event;
//        cmdrNameLabel.setText(loadGame.getCommander());
//    }


}
