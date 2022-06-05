package by.itacademy.account.scheduler.services.scheduler.api;

import by.itacademy.account.scheduler.dto.Schedule;

import java.util.UUID;

public interface ISchedulerService {

    void addScheduledMail(Schedule schedule, UUID mailId);
}
