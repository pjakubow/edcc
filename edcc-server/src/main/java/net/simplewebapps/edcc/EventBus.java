package net.simplewebapps.edcc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class EventBus {

    private final Set<EventSubscriber> subscribers;

    @Autowired
    public EventBus(Set<EventSubscriber> subscribers) {
        this.subscribers = subscribers;
    }

    public void publish(Event event) {
        subscribers.parallelStream()
                .filter(s -> s.accepts(event.getClass()))
                .forEach(s -> s.onEvent(event));
    }
}
