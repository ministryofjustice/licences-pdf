# Licences PDF Generator

Copied from https://github.com/noms-digital-studio/pdf-generator

Creates PDFs from XHTML templates, substituting placeholder text in the template with caller-supplied values.

### Building and running

Build command (includes running unit and integration tests):

- `./gradlew build`

Run locally:

- `./gradlew run`

Running deployable fat jar (after building):

- `java -jar licencespdf.jar` (in the `build/libs` directory)

Configuration parameters can be supplied via environment variables, e.g.:

- `PORT=4567 ./gradlew run`
- `PORT=4567 java -jar licencespdf.jar`

The service endpoint defaults to local port 8080.

### Usage notes

To generate a PDF, issue a POST request to `/generate` that include the template name and substitution values, e.g.:

```
POST /generate HTTP/1.1
Host: localhost:8080

{
	"templateName": "template",
	"values": {
		"ABC": "123"
	}
}
```

The service will return the PDF binary object as a JSON array of Byte values, e.g. `[37, 80, 68, 70, 45, 49 ..`

Or, to directly access a PDF, issue a GET request to `/generate/templateName?key=val&key2=val`


### Template creation

To create or modify new templates, load or create e.g. a Word Document in [LibreOffice](https://www.libreoffice.org/), 
and alter as necessary to include PLACE_HOLDER text which will be substituted in generated PDFs.

Then choose `File | Export..` from the menu, and export in XHTML `.html` format. This new template should then be made 
available to the PDF Generator service by storing in the `src/main/resources/templates` directory.
