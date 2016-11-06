package net.simplewebapps.edcc;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import net.simplewebapps.edcc.config.ObjectMapperConfig;
import net.simplewebapps.edcc.event.Event;
import net.simplewebapps.edcc.event.Fileheader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class LogParserTest {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private LogParser logParser;

    @Before
    public void setUp() throws Exception {
        logParser = new LogParser(new ObjectMapperConfig().objectMapper());
    }

    @Test
    public void shouldThrowUnknownTypeExceptionOnUnmappedEvent() throws Exception {
        try {
            logParser.parseLine("{ \"timestamp\":\"2016-10-27T22:35:31Z\", \"event\":\"Nonexistent\", \"Health\":0.581245 }");
            fail("Should have thrown UnknownTypeException");
        } catch (LogParser.UnknownTypeException e) {
            assertThat(e.getType()).isEqualTo("Nonexistent");
        }
    }

    @Test
    @Parameters({
            "2016-06-20T21:30:00Z, 2016-06-20 23:30:00", // no-DST
            "2016-11-05T15:40:00Z, 2016-11-05 16:40:00"  // DST
    })
    public void shouldTakeTimezoneIntoAccount(String logTimestamp, String localTimestamp) throws Exception {
        //
        String line = "{ \"timestamp\":\"" + logTimestamp + "\", \"event\":\"Fileheader\" }";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(format(event.getTimestamp())).isEqualTo(localTimestamp);
    }

    @Test
    public void shouldParseEventWhenFieldsInOrder() throws Exception {
        // given
        String line = "{ \"timestamp\":\"2016-10-27T21:39:33Z\", \"event\":\"Fileheader\" }";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(event).isInstanceOf(Fileheader.class);
    }

    @Test
    public void shouldParseEventWhenAdditionalFields() throws Exception {
        // given
        String line = "{ \"timestamp\":\"2016-10-27T21:39:33Z\", \"event\":\"Fileheader\", \"unknownField\":\"BOO!\" }";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(event).isInstanceOf(Fileheader.class);

    }

    private String format(Date timestamp) {
        return new SimpleDateFormat(DATE_FORMAT).format(timestamp);
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}