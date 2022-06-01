package by.itacademy.account.scheduler.services.validation;

import by.itacademy.account.scheduler.dto.ScheduledOperation;
import by.itacademy.account.scheduler.services.api.IValidationService;
import by.itacademy.account.scheduler.services.api.exceptions.FieldError;
import by.itacademy.account.scheduler.services.api.exceptions.MultipleError;
import org.springframework.stereotype.Service;

@Service
public class ScheduledOperationValidationService implements IValidationService<ScheduledOperation> {

    private final ScheduleValidationService scheduleValidationService;
    private final OperationValidationService operationValidationService;

    public ScheduledOperationValidationService(ScheduleValidationService scheduleValidationService, OperationValidationService operationValidationService) {
        this.scheduleValidationService = scheduleValidationService;
        this.operationValidationService = operationValidationService;
    }

    @Override
    public ScheduledOperation validate(ScheduledOperation scheduledOperation) {
        if (scheduledOperation == null) {
            throw new IllegalArgumentException("Scheduled operation can't be null");
        }

        this.scheduleValidationService.validate(scheduledOperation.getSchedule());
        this.operationValidationService.validate(scheduledOperation.getOperation());

        MultipleError errors = new MultipleError();
        if (scheduledOperation.getOperation() == null) {
            errors.addError(new FieldError("operation", "operation can't be null"));
        }
        if (scheduledOperation.getSchedule() == null) {
            errors.addError(new FieldError("schedule", "schedule can't be null"));
        }
        if (errors.getErrors().size() > 0) {
            throw errors;
        }
        return scheduledOperation;
    }
}
