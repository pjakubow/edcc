package net.simplewebapps.edcc.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import net.simplewebapps.edcc.Event;

@JsonTypeName
public class Rank extends Event {

    @JsonProperty("Combat")
    private Integer combat;
    @JsonProperty("Trade")
    private Integer trade;
    @JsonProperty("Explore")
    private Integer explore;
    @JsonProperty("Empire")
    private Integer empire;
    @JsonProperty("Federation")
    private Integer federation;
    @JsonProperty("CQC")
    private Integer cqc;

    public Integer getCombat() {
        return combat;
    }

    public void setCombat(Integer combat) {
        this.combat = combat;
    }

    public Integer getTrade() {
        return trade;
    }

    public void setTrade(Integer trade) {
        this.trade = trade;
    }

    public Integer getExplore() {
        return explore;
    }

    public void setExplore(Integer explore) {
        this.explore = explore;
    }

    public Integer getEmpire() {
        return empire;
    }

    public void setEmpire(Integer empire) {
        this.empire = empire;
    }

    public Integer getFederation() {
        return federation;
    }

    public void setFederation(Integer federation) {
        this.federation = federation;
    }

    public Integer getCqc() {
        return cqc;
    }

    public void setCqc(Integer cqc) {
        this.cqc = cqc;
    }
}
