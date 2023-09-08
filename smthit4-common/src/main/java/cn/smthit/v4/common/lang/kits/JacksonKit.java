package cn.smthit.v4.common.lang.kits;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: ...
 * @author: Bean
 * @date: 2023/1/7  0:59
 */
@Slf4j
public class JacksonKit {

    public static String toJson(Object obj) {

        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        om.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        om.configure(SerializationFeature.INDENT_OUTPUT, true);

        DefaultPrettyPrinter pp = new DefaultPrettyPrinter();
        pp = pp.withObjectIndenter(new DefaultIndenter("  ", "\n"));

        om.setDefaultPrettyPrinter(pp);

        try {
            return om.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}