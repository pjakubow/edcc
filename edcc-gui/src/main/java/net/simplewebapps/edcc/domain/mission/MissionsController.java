package net.simplewebapps.edcc.domain.mission;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.util.Callback;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class MissionsController implements Initializable {

    @FXML private TableView<MissionModel> missionsTable;
    @FXML private TableColumn<MissionModel, LocalDateTime> timestampCol;
    @FXML private TableColumn<MissionModel, LocalDateTime> expiryCol;
    @FXML private TableColumn<MissionModel, LocalDateTime> completedCol;
    @FXML private TableColumn<MissionModel, LocalDateTime> abandonedCol;
    @FXML private TableColumn<MissionModel, LocalDateTime> failedCol;

//    @FXML private TextArea missionDetails;
    @FXML private Text missionName;
    @FXML private Text missionFaction;
    @FXML private Text missionCommodity;
    @FXML private Text missionCount;
    @FXML private Text missionPassengerCount;
    @FXML private Text missionPassengerVIP;
    @FXML private Text missionPassengerWanted;
    @FXML private Text missionPassengerType;
    @FXML private Text missionTarget;
    @FXML private Text missionTargetType;
    @FXML private Text missionTargetFaction;
    @FXML private Text missionExpiryDate;
    @FXML private Text missionDestSystem;
    @FXML private Text missionDestStation;
    @FXML private Text missionReward;
    @FXML private Text missionDonation;
    @FXML private Text missionPermitsAwarded;
    @FXML private Text missionCommodityReward;

    private JavaFxEventSubscriber eventSubscriber;

    private ObservableList<MissionModel> missions = FXCollections.observableArrayList();

    @Autowired
    public MissionsController(JavaFxEventSubscriber eventSubscriber) {
        this.eventSubscriber = eventSubscriber;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        missionsTable.setItems(missions);
        missionsTable.getSortOrder().add(timestampCol);

        Callback<TableColumn<MissionModel, LocalDateTime>, TableCell<MissionModel, LocalDateTime>> localDateTimeCellFactory =
                TextFieldTableCell.forTableColumn(new LocalDateTimeConverter());
        timestampCol.setCellFactory(localDateTimeCellFactory);
        expiryCol.setCellFactory(localDateTimeCellFactory);
        completedCol.setCellFactory(localDateTimeCellFactory);
        abandonedCol.setCellFactory(localDateTimeCellFactory);
        failedCol.setCellFactory(localDateTimeCellFactory);

        missionsTable.getSelectionModel().selectedItemProperty()
                .addListener((a, b, c) -> fillFields(c));

        eventSubscriber.addCallback(MissionAccepted.class, (mission) -> onMissionAccepted((MissionAccepted) mission));
        eventSubscriber.addCallback(MissionAbandoned.class, (mission) -> onMissionAbandoned((MissionAbandoned) mission));
        eventSubscriber.addCallback(MissionFailed.class, (mission) -> onMissionFailed((MissionFailed) mission));
        eventSubscriber.addCallback(MissionCompleted.class, (mission) -> onMissionCompleted((MissionCompleted) mission));
    }

    private void fillFields(MissionModel mission) {
        missionName.setText(mission.getName());
        missionFaction.setText(mission.getFaction());
        missionCommodity.setText(mission.getAccDetails().getCommodity());
        missionCount.setText(stringValue(mission.getAccDetails().getCount()));
        missionPassengerCount.setText(stringValue(mission.getAccDetails().getPassengerCount()));
        missionPassengerVIP.setText(booleanValue(mission.getAccDetails().getPassengerVIPs()));
        missionPassengerWanted.setText(booleanValue(mission.getAccDetails().getPassengerWanted()));
        missionPassengerType.setText(mission.getAccDetails().getPassengerType());
        missionTarget.setText(mission.getAccDetails().getTarget());
        missionTargetType.setText(mission.getAccDetails().getTargetType());
        missionTargetFaction.setText(mission.getAccDetails().getTargetFaction());
        missionExpiryDate.setText(LocalDateTimeConverter.FORMATTER.format(mission.getExpiry()));
        missionDestSystem.setText(mission.getAccDetails().getDestinationSystem());
        missionDestStation.setText(mission.getAccDetails().getDestinationStation());
        missionReward.setText(stringValue(mission.getCplDetails().getReward()));
        missionDonation.setText(stringValue(mission.getCplDetails().getDonation()));
        missionPermitsAwarded.setText(String.join(", ", mission.getCplDetails().getPermitsAwarded()));
        missionCommodityReward.setText(mission.getCplDetails().getCommodityRewards().stream()
                .map(cr -> cr.getName() + ": " + cr.getCount())
                .collect(Collectors.joining(", ")));
    }

    private String stringValue(Number number) {
        if (number == null)
            return "";
        return number.toString();
    }

    private String booleanValue(Boolean value) {
        if (value == null)
            return "";
        return value ? "true" : "false";
    }

    private void onMissionAccepted(MissionAccepted mission) {
        missions.add(new MissionModel(mission));
        missionsTable.sort();
    }

    private void onMissionAbandoned(MissionAbandoned mission) {
        missions.stream()
                .filter(mm -> mm.getId() == mission.getId())
                .findAny()
                .ifPresent(mm -> mm.setAbandoned(mission));
        missionsTable.sort();
    }

    private void onMissionFailed(MissionFailed mission) {
        missions.stream()
                .filter(mm -> mm.getId() == mission.getId())
                .findAny()
                .ifPresent(mm -> mm.setFailed(mission));
        missionsTable.sort();
    }

    private void onMissionCompleted(MissionCompleted mission) {
        missions.stream()
                .filter(mm -> mm.getId() == mission.getId())
                .findAny()
                .ifPresent(mm -> mm.setCompleted(mission));
        missionsTable.sort();
    }

}
