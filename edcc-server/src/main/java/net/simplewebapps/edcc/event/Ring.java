package net.simplewebapps.edcc.event;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ring {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("RingClass")
    private String ringClass;

    @JsonProperty("MassMT")
    private Double mass;

    @JsonProperty("InnerRad")
    private Double innerRadius;

    @JsonProperty("OuterRad")
    private Double outerRadius;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRingClass() {
        return ringClass;
    }

    public void setRingClass(String ringClass) {
        this.ringClass = ringClass;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public Double getInnerRadius() {
        return innerRadius;
    }

    public void setInnerRadius(Double innerRadius) {
        this.innerRadius = innerRadius;
    }

    public Double getOuterRadius() {
        return outerRadius;
    }

    public void setOuterRadius(Double outerRadius) {
        this.outerRadius = outerRadius;
    }
}
