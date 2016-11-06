package net.simplewebapps.edcc.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import net.simplewebapps.edcc.GameMode;

@JsonTypeName
public class LoadGame extends Event {

    @JsonProperty("Commander")
    private String commander;

    @JsonProperty("Ship")
    private String ship;

    @JsonProperty("ShipID")
    private Integer shipId;

    @JsonProperty("StartLanded")
    private Boolean startLanded;

    @JsonProperty("StartDead")
    private Boolean startDead;

    @JsonProperty("GameMode")
    private GameMode gameMode;

    @JsonProperty("Group")
    private String group;

    @JsonProperty("Credits")
    private Long credits;

    @JsonProperty("Loan")
    private Long loan;

    public String getCommander() {
        return commander;
    }

    public void setCommander(String commander) {
        this.commander = commander;
    }

    public String getShip() {
        return ship;
    }

    public void setShip(String ship) {
        this.ship = ship;
    }

    public Integer getShipId() {
        return shipId;
    }

    public void setShipId(Integer shipId) {
        this.shipId = shipId;
    }

    public Boolean getStartLanded() {
        return startLanded;
    }

    public void setStartLanded(Boolean startLanded) {
        this.startLanded = startLanded;
    }

    public Boolean getStartDead() {
        return startDead;
    }

    public void setStartDead(Boolean startDead) {
        this.startDead = startDead;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Long getCredits() {
        return credits;
    }

    public void setCredits(Long credits) {
        this.credits = credits;
    }

    public Long getLoan() {
        return loan;
    }

    public void setLoan(Long loan) {
        this.loan = loan;
    }

    @Override
    public String toString() {
        return "LoadGame{" + "timestamp='" + getTimestamp() + '\'' +
          ", commander='" + commander + '\'' +
          ", ship='" + ship + '\'' +
          ", shipId=" + shipId +
          ", startLanded=" + startLanded +
          ", startDead=" + startDead +
          ", gameMode='" + gameMode + '\'' +
          ", group='" + group + '\'' +
          ", credits=" + credits +
          ", loan=" + loan +
          '}';
    }
}
