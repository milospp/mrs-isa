package isa9.Farmacy.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateSerializer extends StdSerializer<LocalDate> {

    protected LocalDateSerializer(Class<LocalDate> t) {
        super(t);
    }

    protected LocalDateSerializer(JavaType type) {
        super(type);
    }

    protected LocalDateSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    protected LocalDateSerializer(StdSerializer<?> src) {
        super(src);
    }

    public LocalDateSerializer() {
        super(LocalDate.class);
    }

    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedString = value.format(formatter);
        gen.writeString(formattedString);
    }
}
