package net.simplewebapps.edcc.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import net.simplewebapps.edcc.Event;

@JsonTypeName
public class NewCommander extends Event {

    /**
     * (new) commander name
     */
    @JsonProperty("Name")
    private String name;

    /**
     * selected starter package
     */
    @JsonProperty("Package")
    private String starterPackage;

    /**
     * @return (new) commander name
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return selected starter package
     */
    public String getStarterPackage() {
        return starterPackage;
    }

    public void setStarterPackage(String starterPackage) {
        this.starterPackage = starterPackage;
    }
}
