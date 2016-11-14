package net.simplewebapps.edcc.domain.exploration;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.TextFieldTableCell;
import net.simplewebapps.edcc.util.JavaFxEventSubscriber;
import net.simplewebapps.edcc.event.Scan;
import net.simplewebapps.edcc.util.LocalDateTimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

@Component
public class ExplorationController implements Initializable {

    @FXML private TextArea scanDetails;
    @FXML private TableView<ExplorationModel> explorationTable;
    @FXML private TableColumn<ExplorationModel, LocalDateTime> timestampCol;

    private ObservableList<ExplorationModel> explorationData = FXCollections.observableArrayList();

    private JavaFxEventSubscriber eventSubscriber;

    @Autowired
    public ExplorationController(JavaFxEventSubscriber eventSubscriber) {
        this.eventSubscriber = eventSubscriber;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timestampCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateTimeConverter()));
        explorationTable.setItems(explorationData);
        explorationTable.getSelectionModel().selectedItemProperty().addListener((a, b, c) -> scanDetails.setText(c.getDetails().toFormattedString()));
        eventSubscriber.addCallback(Scan.class, (scan) -> onScanEvent((Scan) scan));
    }

    private void onScanEvent(Scan scan) {
        explorationData.add(new ExplorationModel(scan));
    }
}
