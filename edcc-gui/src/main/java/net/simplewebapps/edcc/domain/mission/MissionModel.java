package net.simplewebapps.edcc.domain.mission;

import javafx.beans.property.*;
import net.simplewebapps.edcc.event.MissionAbandoned;
import net.simplewebapps.edcc.event.MissionAccepted;
import net.simplewebapps.edcc.event.MissionCompleted;
import net.simplewebapps.edcc.event.MissionFailed;
import net.simplewebapps.edcc.util.DateUtil;

import java.time.LocalDateTime;

public class MissionModel {

    private final LongProperty id;
    private final StringProperty name;
    private final StringProperty faction;
    private final ObjectProperty<LocalDateTime> time;
    private final ObjectProperty<LocalDateTime> expiry;
    private final ObjectProperty<LocalDateTime> abandoned;
    private final ObjectProperty<LocalDateTime> failed;
    private final ObjectProperty<LocalDateTime> completed;
    private final ObjectProperty<MissionState> state;

    private final ObjectProperty<MissionAccepted> accDetails;
    private final ObjectProperty<MissionCompleted> cplDetails;

    MissionModel(MissionAccepted mission) {
        this.id = new SimpleLongProperty(this, "id", mission.getId());
        this.name = new SimpleStringProperty(this, "name", mission.getName());
        this.faction = new SimpleStringProperty(this, "faction", mission.getFaction());
        this.time = new SimpleObjectProperty<>(this, "time", DateUtil.toLocalDateTime(mission.getTimestamp()));
        this.state = new SimpleObjectProperty<>(this, "state", MissionState.ACCEPTED);

        LocalDateTime expiry = null;
        if (mission.getExpiry() != null) {
            expiry = DateUtil.toLocalDateTime(mission.getExpiry());
        }
        this.expiry = new SimpleObjectProperty<>(this, "expiry", expiry);
        this.abandoned = new SimpleObjectProperty<>(this, "abandoned", null);
        this.failed = new SimpleObjectProperty<>(this, "failed", null);
        this.completed = new SimpleObjectProperty<>(this, "completed", null);

        this.accDetails = new SimpleObjectProperty<>(this, "accDetails", mission);
        this.cplDetails = new SimpleObjectProperty<>(this, "cplDetails", null);
    }

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getFaction() {
        return faction.get();
    }

    public StringProperty factionProperty() {
        return faction;
    }

    public LocalDateTime getTime() {
        return time.get();
    }

    public ObjectProperty<LocalDateTime> timeProperty() {
        return time;
    }

    public LocalDateTime getExpiry() {
        return expiry.get();
    }

    public ObjectProperty<LocalDateTime> expiryProperty() {
        return expiry;
    }

    public MissionState getState() {
        return state.get();
    }

    public MissionAccepted getAccDetails() {
        return accDetails.get();
    }

    public ObjectProperty<MissionAccepted> accDetailsProperty() {
        return accDetails;
    }

    public MissionCompleted getCplDetails() {
        return cplDetails.get();
    }

    public ObjectProperty<MissionCompleted> cplDetailsProperty() {
        return cplDetails;
    }

    public ObjectProperty<MissionState> stateProperty() {
        return state;
    }

    public LocalDateTime getAbandoned() {
        return abandoned.get();
    }

    public ObjectProperty<LocalDateTime> abandonedProperty() {
        return abandoned;
    }

    public LocalDateTime getFailed() {
        return failed.get();
    }

    public ObjectProperty<LocalDateTime> failedProperty() {
        return failed;
    }

    public LocalDateTime getCompleted() {
        return completed.get();
    }

    public ObjectProperty<LocalDateTime> completedProperty() {
        return completed;
    }

    public void setAbandoned(MissionAbandoned mission) {
        state.set(MissionState.ABANDONED);
        abandoned.set(DateUtil.toLocalDateTime(mission.getTimestamp()));
    }

    public void setFailed(MissionFailed mission) {
        state.set(MissionState.FAILED);
        failed.set(DateUtil.toLocalDateTime(mission.getTimestamp()));
    }

    public void setCompleted(MissionCompleted mission) {
        state.set(MissionState.COMPLETED);
        completed.set(DateUtil.toLocalDateTime(mission.getTimestamp()));
        cplDetails.set(mission);
    }
}
