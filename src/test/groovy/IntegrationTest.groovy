import groovy.json.JsonSlurper
import groovyx.net.http.RESTClient
import spark.Spark
import spock.lang.Ignore
import spock.lang.Specification
import uk.gov.justice.digital.licences.pdf.Configuration
import uk.gov.justice.digital.licences.pdf.Server

import static groovyx.net.http.ContentType.*

class IntegrationTest extends Specification {

    def jsonSlurper = new JsonSlurper()

    def "server returns health endpoint details"() {

        when:
        def result = new RESTClient('http://localhost:8080/').get(path: 'health')

        then:
        result.status == 200
        result.data.status == "OK"
        result.data.version == "UNKNOWN"
        result.data.configuration == jsonSlurper.parseText("""{"PORT": "8080"}""")
    }

    def "POST generate creates a PDF and returns as a JSON string of Bytes"() {

        when:
        def result = new RESTClient('http://localhost:8080/').post(
                path: 'generate',
                requestContentType: JSON,
                body: [templateName: 'template', values: [ABC: 'ABC1234D']]
        )

        then:
        result.status == 200
        result.data.subList(0, 5) == [37, 80, 68, 70, 45]
        result.data.size > 1000
    }

    def setupSpec() {
        Server.run(new Configuration())
        Thread.sleep 1500
    }

    def cleanupSpec() {
        Spark.stop()
        Thread.sleep 3500
    }

}
