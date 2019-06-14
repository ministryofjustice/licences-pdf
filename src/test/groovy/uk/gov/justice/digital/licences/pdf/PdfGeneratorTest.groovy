package uk.gov.justice.digital.licences.pdf

import org.apache.commons.io.IOUtils
import spock.lang.Specification
import uk.gov.justice.digital.licences.pdf.data.PdfRequest
import uk.gov.justice.digital.licences.pdf.service.PdfGenerator
import uk.gov.justice.digital.licences.pdf.service.TemplateEngine
import uk.gov.justice.digital.licences.pdf.service.TemplateRepository

class PdfGeneratorTest extends Specification {

    def "PdfGenerator processes a PdfRequest and creates a PDF from a template (as ints)"() {

        setup:
        def pdfGenerator = new PdfGenerator(new TemplateEngine(new TemplateRepository()))
        def pdfRequest = new PdfRequest('template', [ABC: 'ABC1234D'])

        when:
        def result = pdfGenerator.generate(pdfRequest).body

        then:
        result[0..5] == [37, 80, 68, 70, 45, 49].collect { it.byteValue() }
        result.length > 1000
    }

    def "PdfGenerator processes a PdfRequest and creates a PDF from a template"() {

        setup:
        def pdfGenerator = new PdfGenerator(new TemplateEngine(new TemplateRepository()))
        def pdfRequest = new PdfRequest('template', [ABC: 'ABC1234D'])

        when:
        def result = pdfGenerator.generatePdf(pdfRequest).body

        then:
        result[0..5] == [37, 80, 68, 70, 45, 49].collect { it.byteValue() }
        result.length > 1000
    }

}
