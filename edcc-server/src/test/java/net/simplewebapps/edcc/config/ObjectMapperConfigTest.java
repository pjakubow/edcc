package net.simplewebapps.edcc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.simplewebapps.edcc.JournalReader;
import net.simplewebapps.edcc.event.*;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class ObjectMapperConfigTest {

    @Test
    public void shouldRegisterAllSubtypes() throws Exception {
        ObjectMapper mapper = new ObjectMapperConfig().objectMapper();

        assertThat(mapper, not(registers(JournalReader.class)));

        assertThat(mapper, registers(Fileheader.class));
        assertThat(mapper, registers(LoadGame.class));
        assertThat(mapper, registers(ClearSavedGame.class));
        assertThat(mapper, registers(NewCommander.class));
        assertThat(mapper, registers(Progress.class));
        assertThat(mapper, registers(Rank.class));
    }

    private ObjectMapperMatcher registers(Class<?> subtype) {
        return new ObjectMapperMatcher(subtype);
    }
}