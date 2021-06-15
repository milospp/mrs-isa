package isa9.Farmacy.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestUtil {

    public static String toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();


        SimpleModule module =
                new SimpleModule("DateSerializer", new Version(1, 0, 0, null, null, null));
        module.addSerializer(LocalDate.class, new LocalDateSerializer());
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        //mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(module);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return mapper.writeValueAsString(object);
    }

}

