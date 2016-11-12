package net.simplewebapps.edcc;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommodityReward {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Count")
    private Integer count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
