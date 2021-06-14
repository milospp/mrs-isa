package isa9.Farmacy.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeSerializer extends StdSerializer<LocalDateTime> {

    protected LocalDateTimeSerializer(Class<LocalDateTime> t) {
        super(t);
    }

    protected LocalDateTimeSerializer(JavaType type) {
        super(type);
    }

    protected LocalDateTimeSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    protected LocalDateTimeSerializer(StdSerializer<?> src) {
        super(src);
    }

    public LocalDateTimeSerializer() {
        super(LocalDateTime.class);
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedString = value.format(formatter);
        gen.writeString(formattedString);
    }
}
