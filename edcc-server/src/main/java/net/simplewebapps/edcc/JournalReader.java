package net.simplewebapps.edcc;

import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;

@Component
public class JournalReader {

    private static final Logger log = LoggerFactory.getLogger(JournalReader.class);

    private final LineProcessor lineProcessor;

    private final Repository repository;

    private long pointer = 0;

    @Autowired
    public JournalReader(LineProcessor lineProcessor, Repository repository) {
        this.lineProcessor = lineProcessor;
        this.repository = repository;
    }

    public void readJournal(String journalFile) {
        pointer = 0;
        try (RandomAccessFile file = new RandomAccessFile(journalFile, "r")) {
            readLoop(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readLoop(RandomAccessFile file) throws InterruptedException, IOException {
        int failures = 0;
        while (true) {
            pointer = file.getFilePointer();
            try {
                repository.save(lineProcessor.process(file.readLine()));
                failures = 0;
            } catch (InvalidTypeIdException itie) {
                log.warn(itie.getMessage());
                continue;
            } catch (Exception e) {
                log.warn(e.getMessage());
                log.info("current pointer: {}, saved pointer: {}", file.getFilePointer(), pointer);
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
}
