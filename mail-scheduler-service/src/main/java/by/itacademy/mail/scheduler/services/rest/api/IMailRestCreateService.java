package by.itacademy.mail.scheduler.services.rest.api;

import by.itacademy.mail.scheduler.dto.wrappers.MailWrapper;

public interface IMailRestCreateService {

    void create(MailWrapper mailWrapper);
}
