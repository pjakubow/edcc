package net.simplewebapps.edcc;

import javafx.application.Platform;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class JavaFxEventSubscriber extends BaseEventSubscriber {

    public void addCallback(Class<? extends Event> clazz, Consumer<Event> callback) {
        super.addCallback(clazz, (event) -> Platform.runLater(() -> callback.accept(event)));
    }
}
