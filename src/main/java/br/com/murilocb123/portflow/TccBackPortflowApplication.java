package br.com.murilocb123.portflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class TccBackPortflowApplication {

    public static void main(String[] args) {
        SpringApplication.run(TccBackPortflowApplication.class, args);
    }

}
