package uk.gov.justice.digital.licences.pdf.service;

import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.xhtmlrenderer.simple.PDFRenderer;
import uk.gov.justice.digital.licences.pdf.data.PdfRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Slf4j
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PdfGenerator {

    private final TemplateEngine engine;

    /**
     * Old endpoint that returns array of ints as json.  Use new generatePdf instead to return byte array.
     */
    @PostMapping(value = "/generate", produces = MediaType.APPLICATION_JSON_VALUE)
    @Deprecated
    public ResponseEntity<int[]> generate(@RequestBody final PdfRequest pdfRequest) {
        try {
            final var body = createPdf(pdfRequest);
            final var ints = new int[body.length];
            for(var i = 0; i < ints.length; i++){
                ints[i] = body[i];
            }
            return ResponseEntity.ok().body(ints);

        } catch (final IOException | DocumentException ex) {
            log.error("Process error", ex);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/generate-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePdf(@RequestBody final PdfRequest pdfRequest) {
        try {
            return ResponseEntity.ok().body(createPdf(pdfRequest));
        } catch (final IOException | DocumentException ex) {
            log.error("Process error", ex);
            return ResponseEntity.badRequest().build();
        }
    }

    private byte[] createPdf(@RequestBody final PdfRequest pdfRequest) throws IOException, DocumentException {
        final byte[] body;
        final var inputFile = createTempFileName("input", ".html");
        final var outputFile = createTempFileName("output", ".pdf");

        final var document = engine.populate(pdfRequest);

        Files.write(inputFile.toPath(), document.getBytes());
        PDFRenderer.renderToPDF(inputFile, outputFile.getCanonicalPath());

        body = Files.readAllBytes(outputFile.toPath());
        return body;
    }

    private File createTempFileName(final String prefix, final String suffix) throws IOException {
        final var tempTile = File.createTempFile(prefix, suffix);
        //noinspection ResultOfMethodCallIgnored
        tempTile.delete();

        return tempTile;
    }
}
