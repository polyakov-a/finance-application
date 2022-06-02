package by.itacademy.mail.services.mail.api;

import by.itacademy.mail.dto.Mail;
import by.itacademy.mail.dto.MailWrapper;
import by.itacademy.mail.dto.PageOfMail;
import org.springframework.data.domain.Pageable;


public interface IMailService {

    Mail create(MailWrapper mailWrapper);

    PageOfMail getAll(Pageable pageable);

    void send(Mail mail, byte[] attachment);
}
