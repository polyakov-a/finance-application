package by.itacademy.account.scheduler.services.scheduled_operation.api;

import by.itacademy.account.scheduler.dto.PageOfScheduledOperation;
import by.itacademy.account.scheduler.dto.ScheduledOperation;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IScheduledOperationService {

    ScheduledOperation create(ScheduledOperation scheduledOperation);

    PageOfScheduledOperation getAll(Pageable pageable, List<UUID> accountIds);

    ScheduledOperation getById(UUID id);

    ScheduledOperation update(ScheduledOperation scheduledOperation, UUID id, LocalDateTime dtUpdate);
}
