package uk.gov.justice.digital.licences.pdf.service;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;

@Component
public class TemplateRepository {

    String get(final String name) {

        try (final var resource = this.getClass().getResourceAsStream(String.format("/templates/%s.html", name))) {
            return IOUtils.toString(resource, Charset.forName("UTF-8"));

        } catch (final IOException ex) {
            return null;
        }
    }
}
