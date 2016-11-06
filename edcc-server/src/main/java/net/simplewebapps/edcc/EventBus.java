package net.simplewebapps.edcc;

import java.util.ArrayList;
import java.util.List;

public class EventBus {

    List<EventSubscriber> subscribers = new ArrayList<>();

    public void publish(Event event) {
        subscribers.stream()
                .filter(s -> s.accepts(event.getClass()))
                .forEach(s -> s.onEvent(event));
    }

    public void addSubscriber(EventSubscriber eventSubscriber) {
        subscribers.add(eventSubscriber);
    }
}
