package br.com.murilocb123.portflow.infra.serializable;


import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class DateConfigure {
    @Bean
    public Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
                .modules(new JavaTimeModule())
                .dateFormat(new StdDateFormat())
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
