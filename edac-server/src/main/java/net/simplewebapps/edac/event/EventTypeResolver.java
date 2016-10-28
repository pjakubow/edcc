package net.simplewebapps.edac.event;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by Ajree on 2016-10-28.
 */
public class EventTypeResolver extends TypeIdResolverBase {

    @Override
    public String idFromValue(Object value) {
        return value.getClass().getSimpleName();
    }

    @Override
    public String idFromValueAndType(Object value, Class<?> suggestedType) {
        return idFromValue(value);
    }

    @Override
    public JavaType typeFromId(DatabindContext context, String id) throws IOException {
        switch (id) {
            case "Fileheader": return TypeFactory.defaultInstance().constructSimpleType(Fileheader.class, null);
            default:  throw new IllegalStateException("Sub-class "+getClass().getName()+" MUST implement "
                    +"`typeFromId(DatabindContext,String)");
        }
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return null;
    }
}
