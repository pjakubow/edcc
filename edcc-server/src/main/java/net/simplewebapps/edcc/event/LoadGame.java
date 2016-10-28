package net.simplewebapps.edcc.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName
public class LoadGame extends Event {

    @JsonProperty("Commander")
    private String commander;

    @JsonProperty("Ship")
    private String ship;

    @JsonProperty("ShipID")
    private Integer shipId;

    @JsonProperty("GameMode")
    private String gameMode;

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

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
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
        return "LoadGame{" + "timestamp='" + timestamp + '\'' +
          ", commander='" + commander + '\'' +
          ", ship='" + ship + '\'' +
          ", shipId=" + shipId +
          ", gameMode='" + gameMode + '\'' +
          ", credits=" + credits +
          ", loan=" + loan +
          '}';
    }
}
