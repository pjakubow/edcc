package net.simplewebapps.edcc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.simplewebapps.edcc.JournalReader;
import net.simplewebapps.edcc.event.Event;
import net.simplewebapps.edcc.event.Fileheader;
import net.simplewebapps.edcc.event.LoadGame;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class ObjectMapperConfigTest {

    @Test
    public void shouldRegisterAllSubtypes() throws Exception {
        ObjectMapper mapper = new ObjectMapperConfig().objectMapper();

        assertThat(mapper, registers(Fileheader.class));
        assertThat(mapper, registers(LoadGame.class));
        assertThat(mapper, not(registers(JournalReader.class)));
    }

    private ObjectMapperMatcher registers(Class<?> subtype) {
        return new ObjectMapperMatcher(subtype);
    }
}