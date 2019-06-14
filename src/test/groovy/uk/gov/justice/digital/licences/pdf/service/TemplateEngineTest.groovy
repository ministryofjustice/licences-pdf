package uk.gov.justice.digital.licences.pdf.service

import spock.lang.Specification
import uk.gov.justice.digital.licences.pdf.data.PdfRequest

class TemplateEngineTest extends Specification {
    private def templateEngine = new TemplateEngine(new TemplateRepository())

    def "Substitutes keys correctly when keys have similar names. Keys ordered shortest to longest "() {
        given:
        PdfRequest request = new PdfRequest('template', [ABC: "value1", ABC_DEF: "value2"])

        when:
        def content = templateEngine.populate(request)

        then:
        content.contains("<p>value1</p>")
        content.contains("<p>value2</p>")
    }

    def "Substitutes keys correctly when keys have similar names. Keys ordered longest to shortest"() {
        given:
        PdfRequest request = new PdfRequest('template', [ABC_DEF: "value2", ABC: "value1"])

        when:
        def content = templateEngine.populate(request)

        then:
        content.contains("<p>value1</p>")
        content.contains("<p>value2</p>")
    }

    def "Substitutes keys correctly when request contains array"() {
        given:
        PdfRequest request = new PdfRequest('template', [MYARRAY: ["arrayvalue1", "arrayvalue2"], MYOTHERARRAY: ["arrayothervalue1", "arrayothervalue2"]])

        when:
        def content = templateEngine.populate(request)

        then:
        content.contains("<p>arrayvalue1</p>")
        content.contains("<p>arrayvalue2</p>")
        content.contains("<p>arrayothervalue1</p>")
        content.contains("<p>arrayothervalue2</p>")
    }

    def "Unused array keys are removed"() {
        given:
        PdfRequest request = new PdfRequest('template', [MYARRAY: ["arrayvalue1"], MYOTHERARRAY: ["arrayothervalue1", "arrayothervalue2"]])

        when:
        def content = templateEngine.populate(request)

        then:
        content.contains("<p>arrayvalue1</p>")
        content.contains("<p></p>")
        !content.contains("MYARRAY[1]")
        content.contains("<p>arrayothervalue1</p>")
        content.contains("<p>arrayothervalue2</p>")
    }
}
