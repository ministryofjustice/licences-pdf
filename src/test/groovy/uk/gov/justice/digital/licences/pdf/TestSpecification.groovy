package uk.gov.justice.digital.licences.pdf


import groovy.util.logging.Slf4j
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("dev")
@ContextConfiguration
@Slf4j
abstract class TestSpecification extends Specification {

    @LocalServerPort
    int randomServerPort;

    @Rule
    TestWatcher t = new TestWatcher() {
        @Override
        protected void starting(Description description) {
            log.info("Starting test '{}'", description.getDisplayName())
        }

        @Override
        protected void finished(Description description) {
            log.info("Finished test '{}'", description.getDisplayName())
        }
    }

    @Autowired
    TestRestTemplate restTemplate

    protected String getBaseUrl() {
        "http://localhost:$randomServerPort"
    }
}
