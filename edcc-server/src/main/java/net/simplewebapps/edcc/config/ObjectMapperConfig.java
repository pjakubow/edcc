package net.simplewebapps.edcc.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.simplewebapps.edcc.event.Event;
import net.simplewebapps.edcc.event.Fileheader;
import net.simplewebapps.edcc.event.LoadGame;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.getSubtypeResolver().registerSubtypes(findSubtypes().toArray(new Class<?>[] {}));
        return objectMapper;
    }

    private List<Class<?>> findSubtypes() {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
        provider.addIncludeFilter(new AssignableTypeFilter(Event.class));
        Set<BeanDefinition> candidateComponents = provider.findCandidateComponents("net/simplewebapps/edcc");
        List<Class<?>> subtypes = new ArrayList<>();
        for (BeanDefinition def : candidateComponents) {
            try {
                subtypes.add(Class.forName(def.getBeanClassName()));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return subtypes;
    }
}
