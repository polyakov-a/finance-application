package by.itacademy.account.services.operation.api;

import by.itacademy.account.dto.Operation;
import by.itacademy.account.dto.PageOfOperation;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IOperationService {

    Operation create(Operation operation, UUID accountId);

    PageOfOperation getAllByAccountId(UUID accountId, Pageable pageable);

    List<Operation> getAllByAccountIds(List<UUID> accountIds);

    Operation update(Operation operation, UUID accountId, UUID operationId, LocalDateTime dtUpdate);

    void delete(UUID accountId, UUID operationId, LocalDateTime dtUpdate);

    Operation getById(UUID id);
}
