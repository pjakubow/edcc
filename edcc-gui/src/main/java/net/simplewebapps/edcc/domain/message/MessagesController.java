package net.simplewebapps.edcc.domain.message;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import net.simplewebapps.edcc.util.JavaFxEventSubscriber;
import net.simplewebapps.edcc.event.ReceiveText;
import net.simplewebapps.edcc.util.LocalDateTimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

@Component
public class MessagesController implements Initializable {

    @FXML private TableView<MessageModel> msgsTable;
    @FXML private TableColumn<MessageModel, LocalDateTime> timestampCol;

    private JavaFxEventSubscriber eventSubscriber;

    private ObservableList<MessageModel> messages = FXCollections.observableArrayList();

    @Autowired
    public MessagesController(JavaFxEventSubscriber eventSubscriber) {
        this.eventSubscriber = eventSubscriber;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        timestampCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateTimeConverter()));

        msgsTable.setItems(messages);

        eventSubscriber.addCallback(ReceiveText.class, (message) -> onMessage((ReceiveText) message));
    }

    private void onMessage(ReceiveText message) {
        messages.add(new MessageModel(message));
    }
}
