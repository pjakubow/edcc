package net.simplewebapps.edac.event;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoadGame {

    @JsonProperty("Commander")
    private String commander;

    @JsonProperty("Ship")
    private String ship;

    @JsonProperty("ShipID")
    private Integer shipId;

    @JsonProperty("GameMode")
    private Integer gameMode;

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

    public Integer getGameMode() {
        return gameMode;
    }

    public void setGameMode(Integer gameMode) {
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
}
