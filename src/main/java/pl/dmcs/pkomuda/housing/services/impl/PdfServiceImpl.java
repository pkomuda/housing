package pl.dmcs.pkomuda.housing.services.impl;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.dmcs.pkomuda.housing.exceptions.ApplicationBaseException;
import pl.dmcs.pkomuda.housing.exceptions.BillNotFoundException;
import pl.dmcs.pkomuda.housing.model.Bill;
import pl.dmcs.pkomuda.housing.model.EmailAttachment;
import pl.dmcs.pkomuda.housing.model.Utility;
import pl.dmcs.pkomuda.housing.repositories.AccountRepository;
import pl.dmcs.pkomuda.housing.repositories.BillRepository;
import pl.dmcs.pkomuda.housing.services.PdfService;
import pl.dmcs.pkomuda.housing.utils.EmailSender;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = ApplicationBaseException.class)
public class PdfServiceImpl implements PdfService {

    private final BillRepository billRepository;

    private final AccountRepository accountRepository;

    private final EmailSender emailSender;

    @Override
    public void generateBillPdf(Long billId, HttpServletResponse response) throws ApplicationBaseException {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(BillNotFoundException::new);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=" + billPdfFileName(bill));
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", LocaleContextHolder.getLocale());
        try (OutputStream outputStream = response.getOutputStream()) {
            writeBillPdf(bill, outputStream, resourceBundle);
        } catch (IOException | DocumentException e) {
            throw new ApplicationBaseException(e);
        }
    }

    @Override
    @Scheduled(cron = "0 0 0 1 * *")
    public void sendLastBillPdfEmails() throws ApplicationBaseException {
        List<Bill> bills = accountRepository.findAll().stream()
                .filter(account -> account.getBills().size() > 0)
                .map(account -> account.getBills().stream()
                        .max(Comparator.comparing(Bill::getIssueDate))
                        .orElse(null))
                .toList();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", Locale.ENGLISH);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            for (Bill bill : bills) {
                if (bill != null) {
                    writeBillPdf(bill, outputStream, resourceBundle);
                    emailSender.sendMessage(bill.getAccount().getEmail(),
                            resourceBundle.getString("email.bill.subject"),
                            resourceBundle.getString("email.bill.text"),
                            new EmailAttachment(billPdfFileName(bill), outputStream.toByteArray(), "application/pdf"));
                    outputStream.reset();
                }
            }
        } catch (IOException | DocumentException e) {
            throw new ApplicationBaseException(e);
        }
    }

    private String billPdfFileName(Bill bill) {
        return "bill" + bill.getId() + "-" + DateTimeFormatter.ofPattern("yyyy-MM-dd").format(bill.getIssueDate()) + ".pdf";
    }

    private void writeBillPdf(Bill bill, OutputStream outputStream, ResourceBundle resourceBundle) throws DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();
        document.add(new Paragraph(resourceBundle.getString("label.bill") + " " + bill.getId()));
        document.add(new Paragraph(Chunk.NEWLINE));
        PdfPTable table = new PdfPTable(2);
        table.addCell(resourceBundle.getString("label.recipient"));
        table.addCell(bill.getAccount().getFirstName() + " " + bill.getAccount().getLastName());
        table.addCell(resourceBundle.getString("bill.issueDate"));
        table.addCell(DateTimeFormatter.ofPattern("dd.MM.yyyy").format(bill.getIssueDate()));
        for (Utility utility : bill.getUtilities()) {
            table.addCell(resourceBundle.getString(utility.getType().label));
            table.addCell(utility.getPrice() + " PLN");
        }
        document.add(table);
        document.close();
    }
}
