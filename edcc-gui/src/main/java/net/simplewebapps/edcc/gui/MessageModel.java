package net.simplewebapps.edcc.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import net.simplewebapps.edcc.event.ReceiveText;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class MessageModel {

    private final StringProperty from;
    private final StringProperty message;
    private final StringProperty channel;
    private final ObjectProperty<LocalDateTime> time;

    public MessageModel(ReceiveText receiveText) {
        from = new SimpleStringProperty(this, "from", receiveText.getFrom());
        message = new SimpleStringProperty(this, "message", receiveText.getMessage());
        channel = new SimpleStringProperty(this, "channel", receiveText.getChannel().name());
        time = new SimpleObjectProperty<>(this, "time", receiveText.getTimestamp().toInstant().atZone(ZoneId.of("Europe/Warsaw")).toLocalDateTime());
    }

    public String getFrom() {
        return from.get();
    }

    public StringProperty fromProperty() {
        return from;
    }

    public String getMessage() {
        return message.get();
    }

    public StringProperty messageProperty() {
        return message;
    }

    public String getChannel() {
        return channel.get();
    }

    public StringProperty channelProperty() {
        return channel;
    }

    public LocalDateTime getTime() {
        return time.get();
    }

    public ObjectProperty<LocalDateTime> timeProperty() {
        return time;
    }
}
