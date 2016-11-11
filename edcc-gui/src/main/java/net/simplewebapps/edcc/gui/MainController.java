package net.simplewebapps.edcc.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import net.simplewebapps.edcc.JavaFxEventSubscriber;
import net.simplewebapps.edcc.event.ReceiveText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

@Component
public class MainController implements Initializable {


    @FXML private TableView<MessageModel> msgsTable;
    @FXML private TableColumn<MessageModel, LocalDateTime> timestampCol;

    @FXML private Tab missionsTab;
    @FXML private Tab messagesTab;

    private ObservableList<MessageModel> messages = FXCollections.observableArrayList();

    private JavaFxEventSubscriber eventSubscriber;

    @Autowired
    public MainController(JavaFxEventSubscriber eventSubscriber) {
        this.eventSubscriber = eventSubscriber;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

        timestampCol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<LocalDateTime>() {

            @Override
            public String toString(LocalDateTime localDateTime) {
                if (localDateTime == null)
                    return "";
                return formatter.format(localDateTime);
            }

            @Override
            public LocalDateTime fromString(String string) {
                return LocalDateTime.parse(string, formatter);
            }
        }));

        msgsTable.setItems(messages);

        eventSubscriber.addCallback(ReceiveText.class, (message) -> onMessage((ReceiveText) message));
    }

    private void onMessage(ReceiveText message) {
        messages.add(new MessageModel(message));
    }

}