package net.simplewebapps.edcc;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

abstract public class BaseEventSubscriber implements EventSubscriber {

    private Map<Class<? extends Event>, Consumer<Event>> callbacks = new HashMap<>();

    @Override
    public boolean accepts(Class<? extends Event> eventClass) {
        return callbacks.containsKey(eventClass);
    }

    @Override
    public void onEvent(Event event) {
        callbacks.get(event.getClass()).accept(event);
    }

    protected void addCallback(Class<? extends Event> clazz, Consumer<Event> callback) {
        callbacks.put(clazz, callback);
    }
}
