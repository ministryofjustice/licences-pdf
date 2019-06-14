package uk.gov.justice.digital.licences.pdf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Server {

    public static void main(final String[] args) {
        log.info("Started Licences PDF Generator Service ...");
        SpringApplication.run(Server.class, args);
    }
}
