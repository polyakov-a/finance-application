package by.itacademy.mail.scheduler.services.validation;

import by.itacademy.mail.scheduler.dto.Mail;
import by.itacademy.mail.scheduler.services.validation.api.IValidationService;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class MailValidationService implements IValidationService<Mail> {

    @Override
    public Mail validate(Mail mail) {
        if (mail == null) {
            throw new IllegalArgumentException("Mail can't be null");
        }
        if (mail.getTo() == null || mail.getTo().isEmpty()) {
            throw new IllegalArgumentException("Invalid e-by.itacademy.mail value");
        }
        boolean valid = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
                .matcher(mail.getTo())
                .matches();
        if (!valid) {
            throw new IllegalArgumentException("Invalid mail value");
        }
        return mail;
    }
}
