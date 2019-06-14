package uk.gov.justice.digital.licences.pdf.service


import spock.lang.Specification

class PingEndpointTest extends Specification {
    def "Ping request returns pong"() {
        when:
        def response = new PingEndpoint().ping()

        then:
        response == "pong"
    }
}
