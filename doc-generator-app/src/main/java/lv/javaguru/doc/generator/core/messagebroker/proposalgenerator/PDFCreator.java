package lv.javaguru.doc.generator.core.messagebroker.proposalgenerator;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Path;
import lv.javaguru.doc.generator.core.api.dto.AgreementDTO;
import lv.javaguru.doc.generator.core.api.dto.PersonDTO;
import lv.javaguru.doc.generator.core.api.dto.RiskDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("checkstyle:magicnumber")
class PDFCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(PDFCreator.class);

    void createPdf(AgreementDTO agreementDTO, Path path) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            InputStream fontStream = getClass().getClassLoader().getResourceAsStream("fonts/arial.ttf");
            PDType0Font font = PDType0Font.load(document, fontStream);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(font, 12);
                contentStream.setLeading(15f);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Agreement Proposal");
                contentStream.newLine();
                contentStream.newLine();

                printAligned(contentStream, "Agreement date from", agreementDTO.agreementDateFrom().toString());
                printAligned(contentStream, "Agreement date to", agreementDTO.agreementDateTo().toString());
                printAligned(contentStream, "Country", agreementDTO.country());
                printAligned(contentStream, "Agreement premium", agreementDTO.agreementPremium().toString());
                contentStream.newLine();

                for (PersonDTO person : agreementDTO.persons()) {
                    contentStream.showText("Person information:");
                    contentStream.newLine();
                    printAligned(contentStream, "First name", person.personFirstName());
                    printAligned(contentStream, "Last name", person.personLastName());
                    printAligned(contentStream, "Personal code", person.personalCode());
                    printAligned(contentStream, "Birth date", person.personBirthDate().toString());
                    printAligned(contentStream, "Medical risk limit level", person.medicalRiskLimitLevel());
                    printAligned(contentStream, "Travel cost", person.travelCost().toString());
                    printAligned(contentStream, "Person premium", convertPersonPremium(person).toString());
                    contentStream.newLine();

                    contentStream.showText("Person risks:");
                    contentStream.newLine();
                    for (RiskDTO risk : person.personRisks()) {
                        printAligned(contentStream, "Risk code", risk.riskIc());
                        printAligned(contentStream, "Premium", risk.premium().toString());
                    }
                    contentStream.newLine();
                }

                contentStream.endText();
            }

            document.save(path.toFile());
        } catch (IOException e) {
            LOGGER.error("Error writing to PDF at path: {}", path, e);
            throw e;
        }
    }

    private void printAligned(PDPageContentStream contentStream, String property, String value) throws IOException {
        contentStream.showText(property + " :");
        contentStream.newLineAtOffset(150, 0);

        contentStream.showText(value);
        contentStream.newLineAtOffset(-150, -15);
    }

    private BigDecimal convertPersonPremium(PersonDTO person) {
        return person.personRisks().stream()
                .map(RiskDTO::premium)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
