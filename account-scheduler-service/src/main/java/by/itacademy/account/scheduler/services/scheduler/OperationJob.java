package by.itacademy.account.scheduler.services.scheduler;

import by.itacademy.account.scheduler.dto.Operation;
import by.itacademy.account.scheduler.dto.ScheduledOperation;
import by.itacademy.account.scheduler.services.rest.OperationRestCreateService;
import by.itacademy.account.scheduler.services.scheduled_operation.ScheduledOperationService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@Transactional
public class OperationJob implements Job {

    private final ScheduledOperationService operationService;
    private final OperationRestCreateService restCreateService;

    public OperationJob(ScheduledOperationService operationService,
                        OperationRestCreateService restCreateService) {
        this.operationService = operationService;
        this.restCreateService = restCreateService;
    }


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        String scheduledOperationId = jobExecutionContext.getMergedJobDataMap().getString("operation");
        ScheduledOperation scheduledOperation = this.operationService.getById(UUID.fromString(scheduledOperationId));
        Operation operation = scheduledOperation.getOperation();
        UUID accountId = operation.getAccount();
        this.restCreateService.create(operation, accountId);
    }
}
