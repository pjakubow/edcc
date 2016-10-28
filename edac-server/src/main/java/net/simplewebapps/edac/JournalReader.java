package net.simplewebapps.edac;

import net.simplewebapps.edac.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.ParseException;
import java.time.Clock;
import java.time.LocalDateTime;

@Component
public class JournalReader {

    private static final Logger log = LoggerFactory.getLogger(JournalReader.class);
    private long pointer = 0;

    private LineProcessor lineProcessor;

    @Autowired
    public JournalReader(LineProcessor lineProcessor) {
        this.lineProcessor = lineProcessor;
    }

    //    @Scheduled(fixedRateString = "${edac.reader.delay}")
    public void readJornal() {
        log.info("Hey! It's {}", LocalDateTime.now(Clock.systemDefaultZone()));
        pointer = 0;
        try (RandomAccessFile file = new RandomAccessFile(findNewestLogfile(), "r")) {
            readLoop(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readLoop(RandomAccessFile file) throws InterruptedException, IOException {
        int failures = 0;
        while (true) {
            pointer = file.getFilePointer();
            log.info("saved pointer: {}", pointer);
            try {
                Event event = lineProcessor.process(file.readLine());
                log.info("got event: {}", event);
                failures = 0;
            } catch (Exception e) {
                log.warn(e.getMessage());
                log.info("real pointer: {}, saved pointer: {}", file.getFilePointer(), pointer);
                Thread.sleep(10000L);
                file.seek(pointer);
                failures++;
                continue;
            }
        }
    }

    private void process(String line) throws ParseException {
        log.info("processing line: {}", line);
        if (line == null) {
            throw new ParseException("boo!", 2);
        }

        if (line.contains("Type6"))
            throw new ParseException("boo2!", 2);
    }

    private String findNewestLogfile() {
        return "D:\\work\\workspaces\\edac\\edac-server\\src\\test\\resources\\Journal.161025234203.01.log";
    }
}
