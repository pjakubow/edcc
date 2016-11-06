package net.simplewebapps.edcc.event;

import net.simplewebapps.edcc.Event;

import java.util.Date;

public class UnknownTypeEvent extends Event {

    private final String type;

    public UnknownTypeEvent(String type) {
        setTimestamp(new Date());
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
