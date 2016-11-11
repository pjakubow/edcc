package net.simplewebapps.edcc.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import net.simplewebapps.edcc.Channel;
import net.simplewebapps.edcc.Event;
import org.springframework.util.StringUtils;

@JsonTypeName
public class ReceiveText extends Event {

    @JsonProperty("From")
    private String from;

    @JsonProperty("From_Localised")
    private String fromLocalised;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("Message_Localised")
    private String messageLocalised;

    @JsonProperty("Channel")
    private Channel channel;

    public String getFrom() {
        return StringUtils.isEmpty(fromLocalised) ? from : fromLocalised;
    }

    public String getRawFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromLocalised() {
        return fromLocalised;
    }

    public void setFromLocalised(String fromLocalised) {
        this.fromLocalised = fromLocalised;
    }

    public String getMessage() {
        return StringUtils.isEmpty(messageLocalised) ? message : messageLocalised;
    }

    public String getRawMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageLocalised() {
        return messageLocalised;
    }

    public void setMessageLocalised(String messageLocalised) {
        this.messageLocalised = messageLocalised;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
