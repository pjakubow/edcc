package net.simplewebapps.edcc.subscriber;

import net.simplewebapps.edcc.BaseEventSubscriber;
import net.simplewebapps.edcc.event.Fileheader;
import net.simplewebapps.edcc.event.LoadGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartupEventSubscriber extends BaseEventSubscriber {

    private final static Logger log = LoggerFactory.getLogger(StartupEventSubscriber.class);

    public StartupEventSubscriber() {
        addCallback(Fileheader.class, event -> this.onFileheader((Fileheader) event));
        addCallback(LoadGame.class,   event -> this.onLoadGame((LoadGame) event));
    }

    private void onFileheader(Fileheader fileheader) {
        log.info("onFileheader {}", fileheader);
    }

    private void onLoadGame(LoadGame loadGame) {
        log.info("onLoadGame {}", loadGame);
    }
}
