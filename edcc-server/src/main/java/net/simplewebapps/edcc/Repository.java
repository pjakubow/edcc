package net.simplewebapps.edcc;

import net.simplewebapps.edcc.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Repository {

    private static final Logger log = LoggerFactory.getLogger(Repository.class);

    public void save(Event event) {
        log.info("Saving event {}", event);
    }

    public void saveUnknownType(String type) {
        log.info("Saving unknown type {}", type);
    }
}
