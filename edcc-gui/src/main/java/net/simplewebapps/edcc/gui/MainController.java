package net.simplewebapps.edcc.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import net.simplewebapps.edcc.util.JavaFxEventSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MainController implements Initializable {

    @FXML private Tab messagesTab;
    @FXML private Tab missionsTab;
    @FXML private Tab explorationTab;

    private JavaFxEventSubscriber eventSubscriber;

    @Autowired
    public MainController(JavaFxEventSubscriber eventSubscriber) {
        this.eventSubscriber = eventSubscriber;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
