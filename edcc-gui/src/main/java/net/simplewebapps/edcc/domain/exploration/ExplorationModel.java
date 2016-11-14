package net.simplewebapps.edcc.domain.exploration;

import javafx.beans.property.*;
import net.simplewebapps.edcc.event.Scan;
import net.simplewebapps.edcc.util.DateUtil;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

public class ExplorationModel {

    public static final String PLANET = "Planet";
    public static final String STAR = "Star";
    private final Scan details;

    private ObjectProperty<LocalDateTime> time;
    private StringProperty name;
    private StringProperty bodyType;
    private StringProperty bodyClass;
    private BooleanProperty landable;
    private DoubleProperty gravity;

    public ExplorationModel(Scan scan) {
        this.time = new SimpleObjectProperty<>(this, "", DateUtil.toLocalDateTime(scan.getTimestamp()));
        this.name = new SimpleStringProperty(this, "", scan.getName());
        String bodyType = StringUtils.isEmpty(scan.getStarType()) ? PLANET : STAR;
        this.bodyType = new SimpleStringProperty(this, "", bodyType);
        this.bodyClass = new SimpleStringProperty(this, "", PLANET.equals(bodyType) ? scan.getPlanetClass() : scan.getStarType());
        this.landable = new SimpleBooleanProperty(this, "", PLANET.equals(bodyType) ? scan.getLandable() : false);
        this.gravity = new SimpleDoubleProperty(this, "", scan.getSurfaceGravity() == null ? -1d : scan.getSurfaceGravity());
        this.details = scan;
    }

    public LocalDateTime getTime() {
        return time.get();
    }

    public ObjectProperty<LocalDateTime> timeProperty() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time.set(time);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getBodyType() {
        return bodyType.get();
    }

    public StringProperty bodyTypeProperty() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType.set(bodyType);
    }

    public String getBodyClass() {
        return bodyClass.get();
    }

    public StringProperty bodyClassProperty() {
        return bodyClass;
    }

    public void setBodyClass(String bodyClass) {
        this.bodyClass.set(bodyClass);
    }

    public boolean isLandable() {
        return landable.get();
    }

    public BooleanProperty landableProperty() {
        return landable;
    }

    public void setLandable(boolean landable) {
        this.landable.set(landable);
    }

    public double getGravity() {
        return gravity.get();
    }

    public DoubleProperty gravityProperty() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity.set(gravity);
    }

    public Scan getDetails() {
        return details;
    }
}
