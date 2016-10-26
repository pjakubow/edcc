package net.simplewebapps.edac;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

@Component
public class JournalReader {

    private static final Logger log = LoggerFactory.getLogger(JournalReader.class);

    @Scheduled(fixedRateString = "${edac.reader.delay}")
    public void readJornal() {
        log.info("Hey! It's {}", LocalDateTime.now(Clock.systemDefaultZone()));
    }
}
