package uk.gov.justice.digital.licences.pdf;

import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import java.util.Map;

import uk.gov.justice.digital.licences.pdf.interfaces.TemplateRepository;
import uk.gov.justice.digital.licences.pdf.mixin.EnvironmentBinder;
import uk.gov.justice.digital.licences.pdf.service.ResourceRepository;

public class Configuration extends AbstractModule implements EnvironmentBinder {

    @Override
    public Map<String, String> envDefaults() {

        return ImmutableMap.of(
                "PORT", "8080"
        );
    }

    @Override
    protected final void configure() {

        bindConfiguration(
                Integer.class,
                Integer::parseInt,
                ImmutableMap.of(
                        "port", "PORT"
                )
        );

        configureOverridable();
    }

    protected void configureOverridable() {
        bind(TemplateRepository.class).to(ResourceRepository.class);
    }

    @Override
    public final Binder binder() {
        return super.binder();  // Mix in binder() from AbstractModule to EnvironmentBinder
    }
}
