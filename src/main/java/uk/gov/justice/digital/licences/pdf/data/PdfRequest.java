package uk.gov.justice.digital.licences.pdf.data;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PdfRequest {

    private String templateName;

    private Map<String, Object> values;
}
