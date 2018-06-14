import spock.lang.Specification
import uk.gov.justice.digital.licences.pdf.data.PdfRequest
import uk.gov.justice.digital.licences.pdf.service.PdfGenerator
import uk.gov.justice.digital.licences.pdf.service.ResourceRepository

class PdfGeneratorTest extends Specification {

    def "PdfGenerator processes a PdfRequest and creates a PDF from a template"() {

        setup:
        def pdfGenerator = new PdfGenerator(new ResourceRepository())
        def pdfRequest = new PdfRequest('template', [ABC: 'ABC1234D'])

        when:
        def result = pdfGenerator.process(pdfRequest)

        then:
        result[0..5] == [37, 80, 68, 70, 45, 49].collect { it.byteValue() }
        result.length > 1000
    }

    def "PdfGenerator processes XML characters successfully"() {

        setup:
        def pdfGenerator = new PdfGenerator(new ResourceRepository())
        def pdfRequest = new PdfRequest('template', [ABC: 'This is "quoted", with <angles> & \' '])

        when:
        def result = pdfGenerator.process(pdfRequest)

        then:
        result[0..5] == [37, 80, 68, 70, 45, 49].collect { it.byteValue() }
        result.length > 1000
    }
}
