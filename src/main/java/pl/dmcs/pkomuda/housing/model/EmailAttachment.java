package pl.dmcs.pkomuda.housing.model;

import lombok.Data;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

@Data
public class EmailAttachment {

    private String fileName;

    private DataSource dataSource;

    public EmailAttachment(String fileName, byte[] data, String type) {
        this.fileName = fileName;
        this.dataSource = new ByteArrayDataSource(data, type);
    }
}
