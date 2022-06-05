package by.itacademy.mail.scheduler.services.validation;

import by.itacademy.mail.scheduler.dto.Schedule;
import by.itacademy.mail.scheduler.services.validation.api.IValidationService;
import by.itacademy.mail.scheduler.services.api.exceptions.FieldError;
import by.itacademy.mail.scheduler.services.api.exceptions.MultipleError;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ScheduleValidationService implements IValidationService<Schedule> {

    @Override
    public Schedule validate(Schedule schedule) {
        if (schedule == null) {
            throw new IllegalArgumentException("Schedule can't be null");
        }
        MultipleError errors = new MultipleError();
        if (schedule.getStartTime() == null) {
            schedule.setStartTime(LocalDateTime.now());
        } else {
            if (schedule.getStartTime().isBefore(LocalDateTime.now())) {
                errors.addError(new FieldError("start time", "invalid start time value"));
            }
        }
        if (schedule.getInterval() == null) {
            errors.addError(new FieldError("interval", "interval can't be null"));
        }
        if (schedule.getTimeUnit() == null) {
            errors.addError(new FieldError("time unit", "time unit can't be null"));
        }
        if (schedule.getStopTime() == null) {
            errors.addError(new FieldError("stop time", "stop time can't be null"));
        } else {
            if (schedule.getStopTime().isBefore(schedule.getStartTime())) {
                errors.addError(new FieldError("stop time", "stop time can't be before start time"));
            }
            if (schedule.getStartTime().isEqual(schedule.getStopTime())) {
                errors.addError(new FieldError("start time", "start time can't be equal to stop time"));
            }
        }
        if (errors.getErrors().size() > 0) {
            throw errors;
        }
        return schedule;
    }
}
