package by.itacademy.mail.scheduler.services.scheduler.api;

import by.itacademy.mail.scheduler.dto.wrappers.MailWrapper;
import by.itacademy.mail.scheduler.dto.Schedule;


public interface ISchedulerService {

    void addScheduledMail(Schedule schedule, MailWrapper mailWrapper);
}
