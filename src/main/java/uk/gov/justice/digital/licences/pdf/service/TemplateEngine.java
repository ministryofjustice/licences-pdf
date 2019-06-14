package uk.gov.justice.digital.licences.pdf.service;

import lombok.AllArgsConstructor;
import org.apache.commons.text.TextStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.justice.digital.licences.pdf.data.PdfRequest;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TemplateEngine {
    private final TemplateRepository templates;

    private static final Comparator<Map.Entry<String, String>> byEntryKeySize =
            Comparator.<Map.Entry<String, String>> comparingInt(entry -> entry.getKey().length()).reversed();

    public String populate(final PdfRequest pdfRequest) {
        final var document = new TextStringBuilder(templates.get(pdfRequest.getTemplateName()));

        pdfRequest.getValues()
                .entrySet()
                .stream()
                .flatMap(TemplateEngine::flattenListsToArrayNotation)
                .sorted(byEntryKeySize)
                .forEach(entry -> document.replaceAll(entry.getKey(), entry.getValue()));

        return removeExcessArrayElements(pdfRequest, document.toString());
    }

    private static String removeExcessArrayElements(final PdfRequest pdfRequest, final String populatedTemplate) {
        return pdfRequest.getValues()
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() instanceof List)
                .map(entry -> String.format("%s\\[%s\\]", entry.getKey(), ".*"))
                .reduce(populatedTemplate, (template, expression) -> template.replaceAll(expression, ""));
    }

    private static Stream<? extends Map.Entry<String, String>> flattenListsToArrayNotation(final Map.Entry<String, Object> entry) {
        if (entry.getValue() instanceof List) {
            @SuppressWarnings("unchecked") final var listValues = (List<String>) entry.getValue();

            return IntStream.range(0, listValues.size())
                    .mapToObj(index -> Map.entry(String.format("%s[%d]", entry.getKey(), index), listValues.get(index)));
        }

        return Stream.of(Map.entry(entry.getKey(), Optional.ofNullable(entry.getValue()).map(Object::toString).orElse("")));
    }
}
