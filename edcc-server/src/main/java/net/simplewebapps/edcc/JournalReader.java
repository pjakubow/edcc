package net.simplewebapps.edcc;

import net.simplewebapps.edcc.event.UnknownTypeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.RandomAccessFile;

@Component
public class JournalReader {

    private static final Logger log = LoggerFactory.getLogger(JournalReader.class);

    private final LogParser logParser;

    private final EventBus eventBus;

    private long pointer = 0;
    private boolean stopped = false;

    @Autowired
    public JournalReader(LogParser logParser, EventBus eventBus) {
        this.logParser = logParser;
        this.eventBus = eventBus;
    }

    public void readJournal(String journalFile) {
        pointer = 0;
        // FIXME: untestable code
        try (RandomAccessFile file = new RandomAccessFile(journalFile, "r")) {
            readLoop(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopWatchdog() {
        log.info("Stopping watchdog");
        this.stopped = true;
    }

    private void readLoop(RandomAccessFile file) throws InterruptedException, IOException {
        int failures = 0;
        while (!stopped) {
            pointer = file.getFilePointer();
            try {
                eventBus.publish(logParser.parseLine(file.readLine()));
                failures = 0;
            } catch (LogParser.UnknownTypeException ute) {
                log.warn(ute.getMessage());
                eventBus.publish(new UnknownTypeEvent(ute.getType()));
            } catch (Exception e) {
                log.warn(e.getMessage());
                log.info("current pointer: {}, saved pointer: {}", file.getFilePointer(), pointer);
                Thread.sleep((failures  % 10) * 10000L);
                file.seek(pointer);
                failures++;
            }
        }
    }
}
