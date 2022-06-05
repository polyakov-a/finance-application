package by.itacademy.mail.scheduler.services.scheduled_mail.api;

import by.itacademy.mail.scheduler.dto.PageOfScheduledMail;
import by.itacademy.mail.scheduler.dto.ScheduledMail;
import by.itacademy.mail.scheduler.dto.wrappers.ScheduledMailWrapper;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IScheduledMailService {

    ScheduledMail create(ScheduledMailWrapper wrapper);

    PageOfScheduledMail getAll(Pageable pageable);

    ScheduledMail getById(UUID id);

    ScheduledMail update(ScheduledMailWrapper wrapper, UUID id, LocalDateTime dtUpdate);
}
