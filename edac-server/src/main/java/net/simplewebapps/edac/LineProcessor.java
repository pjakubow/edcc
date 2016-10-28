package net.simplewebapps.edac;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.simplewebapps.edac.event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LineProcessor {

    private final ObjectMapper objectMapper;

    @Autowired
    public LineProcessor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public Event process(String line) throws IOException {
        return objectMapper.readValue(line, Event.class);
    }
}
