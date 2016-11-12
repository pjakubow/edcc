package net.simplewebapps.edcc.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import net.simplewebapps.edcc.CommodityReward;
import net.simplewebapps.edcc.Event;
import org.springframework.util.StringUtils;

import java.util.List;

@JsonTypeName
public class MissionCompleted extends Event {

    @JsonProperty("MissionID")
    private Long id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Faction")
    private String faction;

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
    @JsonProperty("DestinationSystem")
    private String destinationSystem;
    @JsonProperty("DestinationStation")
    private String destinationStation;
    @JsonProperty("Reward")
    private Long reward;
    @JsonProperty("Donation")
    private Long donation;
    @JsonProperty("PermitsAwarded")
    private List<String> permitsAwarded;
    @JsonProperty("CommodityReward")
    private List<CommodityReward> commodityRewards;


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

    public Long getReward() {
        return reward;
    }

    public void setReward(Long reward) {
        this.reward = reward;
    }

    public List<CommodityReward> getCommodityRewards() {
        return commodityRewards;
    }

    public void setCommodityRewards(List<CommodityReward> commodityRewards) {
        this.commodityRewards = commodityRewards;
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

    public List<String> getPermitsAwarded() {
        return permitsAwarded;
    }

    public void setPermitsAwarded(List<String> permitsAwarded) {
        this.permitsAwarded = permitsAwarded;
    }

    public Long getDonation() {
        return donation;
    }

    public void setDonation(Long donation) {
        this.donation = donation;
    }

    @Override
    public String toString() {
        return "MissionCompleted{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", faction='" + faction + '\'' +
                ", commodity='" + commodity + '\'' +
                ", commodityLocalised='" + commodityLocalised + '\'' +
                ", count=" + count +
                ", target='" + target + '\'' +
                ", targetType='" + targetType + '\'' +
                ", targetTypeLocalised='" + targetTypeLocalised + '\'' +
                ", targetFaction='" + targetFaction + '\'' +
                ", destinationSystem='" + destinationSystem + '\'' +
                ", destinationStation='" + destinationStation + '\'' +
                ", reward=" + reward +
                ", donation=" + donation +
                ", permitsAwarded=" + permitsAwarded +
                ", commodityRewards=" + commodityRewards +
                '}';
    }
}
