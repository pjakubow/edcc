package net.simplewebapps.edcc.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import net.simplewebapps.edcc.Event;
import org.springframework.util.StringUtils;

import java.util.Date;

@JsonTypeName
public class MissionAccepted extends Event {

    @JsonProperty("MissionID")
    private Long id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Faction")
    private String faction;

    // optional:

    @JsonProperty("Commodity")
    private String commodity;
    @JsonProperty("Commodity_Localised")
    private String commodityLocalised;
    @JsonProperty("Count")
    private Integer count;
    @JsonProperty("Target")
    private String target;
    @JsonProperty("TargetType")
    private String targetType;
    @JsonProperty("TargetType_Localised")
    private String targetTypeLocalised;
    @JsonProperty("TargetFaction")
    private String targetFaction;
    @JsonProperty("Expiry")
    private Date expiry;
    @JsonProperty("DestinationSystem")
    private String destinationSystem;
    @JsonProperty("DestinationStation")
    private String destinationStation;
    @JsonProperty("PassengerCount")
    private Integer passengerCount;
    @JsonProperty("PassengerVIPs")
    private Boolean passengerVIPs;
    @JsonProperty("PassengerWanted")
    private Boolean passengerWanted;
    /**
     * eg Tourist, Soldier, Explorer,...
     */
    @JsonProperty("PassengerType")
    private String passengerType;

    @JsonProperty("PassengerType_Localised")
    private String passengerTypeLocalised;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaction() {
        return faction;
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    public String getCommodity() {
        return StringUtils.isEmpty(commodityLocalised) ? commodity : commodityLocalised;
    }

    public String getRawCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getCommodityLocalised() {
        return commodityLocalised;
    }

    public void setCommodityLocalised(String commodityLocalised) {
        this.commodityLocalised = commodityLocalised;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTargetType() {
        return StringUtils.isEmpty(targetTypeLocalised) ? targetType : targetTypeLocalised;
    }

    public String getRawTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getTargetTypeLocalised() {
        return targetTypeLocalised;
    }

    public void setTargetTypeLocalised(String targetTypeLocalised) {
        this.targetTypeLocalised = targetTypeLocalised;
    }

    public String getTargetFaction() {
        return targetFaction;
    }

    public void setTargetFaction(String targetFaction) {
        this.targetFaction = targetFaction;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public String getDestinationSystem() {
        return destinationSystem;
    }

    public void setDestinationSystem(String destinationSystem) {
        this.destinationSystem = destinationSystem;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(String destinationStation) {
        this.destinationStation = destinationStation;
    }

    public Integer getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(Integer passengerCount) {
        this.passengerCount = passengerCount;
    }

    public Boolean getPassengerVIPs() {
        return passengerVIPs;
    }

    public void setPassengerVIPs(Boolean passengerVIPs) {
        this.passengerVIPs = passengerVIPs;
    }

    public Boolean getPassengerWanted() {
        return passengerWanted;
    }

    public void setPassengerWanted(Boolean passengerWanted) {
        this.passengerWanted = passengerWanted;
    }

    public String getPassengerType() {
        return StringUtils.isEmpty(passengerTypeLocalised) ? passengerType : passengerTypeLocalised;
    }

    public String getRawPassengerType() {
        return passengerType;
    }

    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType;
    }

    public String getPassengerTypeLocalised() {
        return passengerTypeLocalised;
    }

    public void setPassengerTypeLocalised(String passengerTypeLocalised) {
        this.passengerTypeLocalised = passengerTypeLocalised;
    }
}
