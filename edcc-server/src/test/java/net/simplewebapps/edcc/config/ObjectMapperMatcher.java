package net.simplewebapps.edcc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver;
import net.simplewebapps.edcc.event.Event;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Set;

public class ObjectMapperMatcher extends TypeSafeMatcher<ObjectMapper> {

    private final Class<?> subtype;

    public ObjectMapperMatcher(Class<?> subtype) {
        this.subtype = subtype;
    }

    @Override
    protected boolean matchesSafely(ObjectMapper item) {
        try {
            SubtypeResolver subtypeResolver = item.getSubtypeResolver();
            Field registeredSubtypes = StdSubtypeResolver.class.getDeclaredField("_registeredSubtypes");
            registeredSubtypes.setAccessible(true);
            return ((Set<NamedType>) registeredSubtypes.get(subtypeResolver)).stream()
                    .anyMatch(nt -> nt.getType().equals(subtype));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("registered ").appendValue(subtype);
    }
}
