package pl.dmcs.pkomuda.housing.services;

import pl.dmcs.pkomuda.housing.exceptions.ApplicationBaseException;

import javax.servlet.http.HttpServletResponse;

public interface PdfService {

    void generateBillPdf(Long billId, HttpServletResponse response) throws ApplicationBaseException;
    void sendLastBillPdfEmails() throws ApplicationBaseException;
}
