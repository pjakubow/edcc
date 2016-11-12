package net.simplewebapps.edcc.gui;

import javafx.util.StringConverter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter extends StringConverter<LocalDateTime> {

    public final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

    @Override
    public String toString(LocalDateTime localDateTime) {
        if (localDateTime == null)
            return "";
        return FORMATTER.format(localDateTime);
    }

    @Override
    public LocalDateTime fromString(String string) {
        return LocalDateTime.parse(string, FORMATTER);
    }
}
