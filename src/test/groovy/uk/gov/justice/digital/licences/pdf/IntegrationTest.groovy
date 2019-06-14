package uk.gov.justice.digital.licences.pdf

import groovyx.net.http.RESTClient
import org.apache.commons.io.IOUtils

import static groovyx.net.http.ContentType.JSON

class IntegrationTest extends TestSpecification {
    def "POST generate creates a PDF and returns as a JSON string of Bytes"() {

        when:
        def result = new RESTClient(getBaseUrl()).post(
                path: 'generate',
                requestContentType: JSON,
                body: [templateName: 'template', values: [ABC: 'ABC1234D']]
        )

        then:
        result.status == 200
        result.data.subList(0, 5) == [37, 80, 68, 70, 45]
        result.data.size > 1000
        result.headers['Content-Type'].value == 'application/json;charset=UTF-8'
    }

    def "POST generate creates a PDF and returns as an application/pdf"() {

        when:
        def result = new RESTClient(getBaseUrl()).post(
                path: 'generate-pdf',
                requestContentType: JSON,
                body: [templateName: 'template', values: [ABC: 'ABC1234D']]
        )

        then:
        result.status == 200
        byte[] buffer = IOUtils.toByteArray(result.data)
        buffer[0..4] == [37, 80, 68, 70, 45]
        buffer.length > 1000
        result.headers['Content-Type'].value == 'application/pdf'
    }
}
