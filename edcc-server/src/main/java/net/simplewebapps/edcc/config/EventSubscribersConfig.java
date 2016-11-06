package net.simplewebapps.edcc.config;

import net.simplewebapps.edcc.EventBus;
import net.simplewebapps.edcc.subscriber.StartupEventSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventSubscribersConfig {

    @Bean
    public EventBus eventBus() {
        EventBus eventBus = new EventBus();
        eventBus.addSubscriber(new StartupEventSubscriber());
        return eventBus;
    }
}
