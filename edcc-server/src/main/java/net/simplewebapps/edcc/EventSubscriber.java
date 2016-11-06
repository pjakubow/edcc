package net.simplewebapps.edcc;

import net.simplewebapps.edcc.Event;

public interface EventSubscriber {

    boolean accepts(Class<? extends Event> eventClass);

    void onEvent(Event event);
}
