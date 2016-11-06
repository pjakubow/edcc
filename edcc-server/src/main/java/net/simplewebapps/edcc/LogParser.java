package net.simplewebapps.edcc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogParser {

    private final ObjectMapper objectMapper;

    @Autowired
    public LogParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public Event parseLine(String line) {
        try {
            return objectMapper.readValue(line, Event.class);
        } catch (InvalidTypeIdException itie) {
            throw new UnknownTypeException(itie);
        } catch (Exception e) {
            throw new ParseException(e);
        }
    }

    public static class UnknownTypeException extends RuntimeException {
        private final String type;

        public UnknownTypeException(InvalidTypeIdException e) {
            super(e.getMessage(), e);
            this.type = e.getTypeId();
        }

        public String getType() {
            return type;
        }
    }

    public static class ParseException extends RuntimeException {
        public ParseException(Throwable e) {
            super(e);
        }
    }
}
