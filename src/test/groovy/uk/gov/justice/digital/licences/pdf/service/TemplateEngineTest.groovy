package uk.gov.justice.digital.licences.pdf.service

import com.google.common.collect.ImmutableMap
import spock.lang.Specification
import uk.gov.justice.digital.licences.pdf.data.PdfRequest

class TemplateEngineTest extends Specification {

    def "Substitutes keys correctly when keys have similar names. Keys ordered shortest to longest "() {
        given:
        PdfRequest request = new PdfRequest('template', ImmutableMap.of("ABC", "value1", "ABC_DEF", "value2"));

        when:
        def content = TemplateEngine.populate(request, new ResourceRepository());

        then:
        content.contains("<p>value1</p>")
        content.contains("<p>value2</p>")
    }

    def "Substitutes keys correctly when keys have similar names. Keys ordered longest to shortest"() {
        given:
        PdfRequest request = new PdfRequest('template', ImmutableMap.of("ABC_DEF", "value2", "ABC", "value1"));

        when:
        def content = TemplateEngine.populate(request, new ResourceRepository());

        then:
        content.contains("<p>value1</p>")
        content.contains("<p>value2</p>")
    }
    def "Substitutes keys correctly when request contains array"() {
        given:
        PdfRequest request = new PdfRequest('template', ImmutableMap.of("MYARRAY", Arrays.asList("arrayvalue1", "arrayvalue2"), "MYOTHERARRAY", Arrays.asList("arrayothervalue1", "arrayothervalue2")));

        when:
        def content = TemplateEngine.populate(request, new ResourceRepository());

        then:
        content.contains("<p>arrayvalue1</p>")
        content.contains("<p>arrayvalue2</p>")
        content.contains("<p>arrayothervalue1</p>")
        content.contains("<p>arrayothervalue2</p>")
    }

    def "Unused array keys are removed"() {
        given:
        PdfRequest request = new PdfRequest('template', ImmutableMap.of("MYARRAY", Arrays.asList("arrayvalue1"), "MYOTHERARRAY", Arrays.asList("arrayothervalue1", "arrayothervalue2")));

        when:
        def content = TemplateEngine.populate(request, new ResourceRepository());

        then:
        content.contains("<p>arrayvalue1</p>")
        content.contains("<p></p>")
        !content.contains("MYARRAY[1]")
        content.contains("<p>arrayothervalue1</p>")
        content.contains("<p>arrayothervalue2</p>")
    }
}
