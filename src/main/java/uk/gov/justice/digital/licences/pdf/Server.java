package uk.gov.justice.digital.licences.pdf;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import uk.gov.justice.digital.licences.pdf.data.PdfRequest;
import uk.gov.justice.digital.licences.pdf.service.HealthService;
import uk.gov.justice.digital.licences.pdf.service.PdfGenerator;

import java.util.Map;
import java.util.stream.Collectors;

import static spark.Spark.get;
import static spark.Spark.port;
import static uk.gov.justice.digital.licences.pdf.helpers.JsonRoute.getJson;
import static uk.gov.justice.digital.licences.pdf.helpers.JsonRoute.postJson;

@Slf4j
public class Server {

    public static void main(String[] args) {
        log.info("Started Licences PDF Generator Service ...");
        run(new Configuration());
    }

    public static void run(Configuration configuration)  {

        Injector injector = Guice.createInjector(configuration);


        port(injector.getInstance(Key.get(Integer.class, Names.named("port"))));

        getJson("/health", injector.getInstance(HealthService.class)::process);
        postJson("/generate", PdfRequest.class, injector.getInstance(PdfGenerator.class)::process);

        get("/generate/:template", (request, response) -> {
            response.type("application/pdf");
            return ArrayUtils.toPrimitive(
                    injector.getInstance(PdfGenerator.class).process(new PdfRequest(
                                    request.params(":template"),
                                    request.queryMap().toMap().entrySet().stream().collect(
                                            Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue()[0]))
                            )
                    )
            );
        });
    }
}
