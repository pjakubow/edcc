package net.simplewebapps.edcc;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import net.simplewebapps.edcc.config.ObjectMapperConfig;
import net.simplewebapps.edcc.event.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

    @Test
    public void shouldParseClearSavedGameEvent() throws Exception {
        // given
        String line = "{ \"timestamp\":\"2016-06-10T14:32:03Z\", \"event\":\"ClearSavedGame\", \"Name\":\"AJRee\" }";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(event).isInstanceOf(ClearSavedGame.class);
        assertThat(((ClearSavedGame) event).getName()).isEqualTo("AJRee");
    }

    @Test
    public void shouldParseNewCommanderEvent() throws Exception {
        // given
        String line = "{ \"timestamp\":\"2016-06-10T14:32:03Z\", \"event\":\"NewCommander\", \"Name\":\"AJRee\", \"Package\":\"ImperialBountyHunter\" }";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(event).isInstanceOf(NewCommander.class);
        assertThat(((NewCommander) event).getName()).isEqualTo("AJRee");
        assertThat(((NewCommander) event).getStarterPackage()).isEqualTo("ImperialBountyHunter");
    }

    @Test
    public void shouldParseLoadGameEvent() throws Exception {
        // given
        String line = "{ \"timestamp\":\"2016-06-10T14:32:03Z\", \"event\":\"LoadGame\", \"Commander\":\"AJRee\", \"Ship\":\"CobraMkIII\", \"ShipID\":1, \"GameMode\":\"Group\", \"Group\":\"Le Group Name\", \"Credits\":600120, \"Loan\":0  }";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(event).isInstanceOf(LoadGame.class);
        LoadGame loadGameEvent = (LoadGame) event;
        assertThat(loadGameEvent.getCommander()).isEqualTo("AJRee");
        assertThat(loadGameEvent.getShip()).isEqualTo("CobraMkIII");
        assertThat(loadGameEvent.getShipId()).isEqualTo(1);
        assertThat(loadGameEvent.getStartLanded()).isNull();
        assertThat(loadGameEvent.getStartDead()).isNull();
        assertThat(loadGameEvent.getGameMode()).isEqualTo(GameMode.Group);
        assertThat(loadGameEvent.getGroup()).isEqualTo("Le Group Name");
        assertThat(loadGameEvent.getCredits()).isEqualTo(600_120);
        assertThat(loadGameEvent.getLoan()).isEqualTo(0);
    }

    /*

    @Test
    public void shouldParseEvent() throws Exception {
        // given
        String line = "";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(event).isInstanceOf(ClearSavedGame.class);
        assertThat(((ClearSavedGame) event).getName()).isEqualTo("");
    }
    */

    private String format(Date timestamp) {
        ZonedDateTime zonedDateTime = timestamp.toInstant().atZone(ZoneId.of("Europe/Warsaw"));
        return zonedDateTime.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
}