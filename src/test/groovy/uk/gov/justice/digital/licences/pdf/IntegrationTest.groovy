package uk.gov.justice.digital.licences.pdf

import groovyx.net.http.RESTClient

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
    }
}
