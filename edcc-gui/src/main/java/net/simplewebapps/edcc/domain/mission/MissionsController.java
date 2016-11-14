package net.simplewebapps.edcc.domain.mission;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.TextFieldTableCell;
import net.simplewebapps.edcc.util.JavaFxEventSubscriber;
import net.simplewebapps.edcc.event.MissionAbandoned;
import net.simplewebapps.edcc.event.MissionAccepted;
import net.simplewebapps.edcc.event.MissionCompleted;
import net.simplewebapps.edcc.event.MissionFailed;
import net.simplewebapps.edcc.util.LocalDateTimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

@Component
public class MissionsController implements Initializable {

    @FXML private TextArea missionDetails;
    @FXML private TableView<MissionModel> missionsTable;
    @FXML private TableColumn<MissionModel, LocalDateTime> timestampCol;
    @FXML private TableColumn<MissionModel, LocalDateTime> expiryCol;
    @FXML private TableColumn<MissionModel, LocalDateTime> completedCol;
    @FXML private TableColumn<MissionModel, LocalDateTime> abandonedCol;
    @FXML private TableColumn<MissionModel, LocalDateTime> failedCol;

    private JavaFxEventSubscriber eventSubscriber;

    private ObservableList<MissionModel> missions = FXCollections.observableArrayList();

    @Autowired
    public MissionsController(JavaFxEventSubscriber eventSubscriber) {
        this.eventSubscriber = eventSubscriber;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        timestampCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateTimeConverter()));
        expiryCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateTimeConverter()));
        completedCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateTimeConverter()));
        abandonedCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateTimeConverter()));
        failedCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateTimeConverter()));

        missionsTable.setItems(missions);
        missionsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            missionDetails.setText(newSelection.getAccDetails().toString());
        });

        eventSubscriber.addCallback(MissionAccepted.class, (mission) -> onMissionAccepted((MissionAccepted) mission));
        eventSubscriber.addCallback(MissionAbandoned.class, (mission) -> onMissionAbandoned((MissionAbandoned) mission));
        eventSubscriber.addCallback(MissionFailed.class, (mission) -> onMissionFailed((MissionFailed) mission));
        eventSubscriber.addCallback(MissionCompleted.class, (mission) -> onMissionCompleted((MissionCompleted) mission));
    }

    private void onMissionAccepted(MissionAccepted mission) {
        missions.add(new MissionModel(mission));
    }

    private void onMissionAbandoned(MissionAbandoned mission) {
        missions.stream()
                .filter(mm -> mm.getId() == mission.getId())
                .findAny()
                .ifPresent(mm -> mm.setAbandoned(mission));
    }

    private void onMissionFailed(MissionFailed mission) {
        missions.stream()
                .filter(mm -> mm.getId() == mission.getId())
                .findAny()
                .ifPresent(mm -> mm.setFailed(mission));
    }

    private void onMissionCompleted(MissionCompleted mission) {
        missions.stream()
                .filter(mm -> mm.getId() == mission.getId())
                .findAny()
                .ifPresent(mm -> mm.setCompleted(mission));
    }

}
