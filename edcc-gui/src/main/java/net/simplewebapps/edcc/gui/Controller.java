package net.simplewebapps.edcc.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import net.simplewebapps.edcc.JournalReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class Controller implements Initializable {

    @FXML
    private Button ziButton;

    private JournalReader journalReader;

    private Thread watchdogThread;

    @Autowired
    public Controller(JournalReader journalReader) {
        this.journalReader = journalReader;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ziButton.setOnMouseClicked(event -> {
            try {
                if (watchdogThread == null) {
                    watchdogThread = new Thread(() -> journalReader.readJournal(findNewestLogfile()));
                    watchdogThread.setDaemon(true);
                    watchdogThread.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static String findNewestLogfile() {
        return Controller.class.getClassLoader().getResource("Journal.161025234203.01.log").getPath();
    }
}
