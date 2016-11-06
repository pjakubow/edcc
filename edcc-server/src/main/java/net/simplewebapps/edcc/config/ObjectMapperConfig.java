package net.simplewebapps.edcc.config;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.simplewebapps.edcc.Event;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.getSubtypeResolver().registerSubtypes(findSubtypesOf(Event.class).toArray(new Class<?>[] {}));
        return objectMapper;
    }

    private List<Class<?>> findSubtypesOf(Class<?> superClazz) {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
        provider.addIncludeFilter(new AnnotationTypeFilter(JsonTypeName.class));
        Set<BeanDefinition> candidateComponents = provider.findCandidateComponents("net/simplewebapps/edcc");
        List<Class<?>> subtypes = new ArrayList<>();
        for (BeanDefinition def : candidateComponents) {
            try {
                Class<?> clazz = Class.forName(def.getBeanClassName());
                if (superClazz.isAssignableFrom(clazz)) {
                    subtypes.add(clazz);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return subtypes;
    }
}
