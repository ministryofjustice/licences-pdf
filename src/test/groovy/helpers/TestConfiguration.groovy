package helpers

import uk.gov.justice.digital.licences.pdf.Configuration

class TestConfiguration extends Configuration {

    Map<String, String> envDefaults() {
        new HashMap<String, String>(super.envDefaults()) << [PORT: "8081"]
    }
}
