package by.itacademy.account.services.operation;

import by.itacademy.account.dao.OperationRepository;
import by.itacademy.account.dto.Account;
import by.itacademy.account.dto.Operation;
import by.itacademy.account.dto.PageOfOperation;
import by.itacademy.account.entities.AccountEntity;
import by.itacademy.account.entities.OperationEntity;
import by.itacademy.account.services.account.AccountService;
import by.itacademy.account.services.operation.api.IOperationService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OperationService implements IOperationService {

    private final OperationRepository repository;
    private final OperationValidationService validationService;
    private final AccountService accountService;
    private final ModelMapper mapper;

    public OperationService(OperationRepository repository,
                            OperationValidationService validationService,
                            AccountService accountService,
                            ModelMapper mapper) {
        this.repository = repository;
        this.validationService = validationService;
        this.accountService = accountService;
        this.mapper = mapper;
    }

    @Override
    public Operation create(Operation operation, UUID accountId) {
        operation = this.validationService.validate(operation);
        OperationEntity entity = mapper.map(operation, OperationEntity.class);
        LocalDateTime now = LocalDateTime.now();
        entity.setDtCreate(now);
        entity.setDtUpdate(now);
        Account account = this.accountService.updateBalance(accountId, operation.getValue());
        entity.setAccount(this.mapper.map(account, AccountEntity.class));
        return mapper.map(this.repository.save(entity), Operation.class);
    }

    @Override
    public PageOfOperation getAllByAccountId(UUID accountId, Pageable pageable) {
        AccountEntity accountEntity = this.mapper.map(this.accountService.getById(accountId), AccountEntity.class);
        Page<OperationEntity> result = this.repository.findAllByAccount(accountEntity, pageable);

        int totalPages = result.getTotalPages();
        int totalElements = (int) result.getTotalElements();
        boolean first = result.isFirst();
        int numberOfElements = result.getNumberOfElements();
        boolean last = result.isLast();
        List<Operation> content = result.getContent()
                .stream()
                .map(entity -> this.mapper.map(entity, Operation.class))
                .collect(Collectors.toList());

        return PageOfOperation.of(pageable.getPageNumber(), pageable.getPageSize(), totalPages,
                totalElements, first, numberOfElements, last, content);
    }

    @Override
    public List<Operation> getAllByAccountIds(List<UUID> accountIds) {
        return this.repository.findAll()
                .stream()
                .filter(entity -> accountIds.contains(entity.getAccount().getId()))
                .map(entity -> this.mapper.map(entity, Operation.class))
                .collect(Collectors.toList());
    }

    @Override
    public Operation update(Operation operation, UUID accountId, UUID operationId, LocalDateTime dtUpdate) {
        operation = this.validationService.validate(operation);
        OperationEntity entity = this.repository.findById(operationId)
                .orElseThrow(() -> new IllegalArgumentException("Unable to find operation with ID: " + operationId));
        if (!entity.getDtUpdate().truncatedTo(ChronoUnit.MILLIS).isEqual(dtUpdate)) {
            throw new IllegalArgumentException("Invalid dtUpdate value for this operation");
        } else if (!entity.getAccount().getId().equals(accountId)) {
            throw new IllegalArgumentException("Invalid account value for this operation");
        }
        entity.setDate(operation.getDate());
        entity.setDescription(operation.getDescription());
        entity.setCategory(operation.getCategory());
        entity.setCurrency(operation.getCurrency());
        entity.setDtUpdate(LocalDateTime.now());
        AccountEntity account = this.mapper.map(this.accountService.updateBalance(
                        accountId, operation.getValue().subtract(entity.getValue())), AccountEntity.class);
        entity.setValue(operation.getValue());
        entity.setAccount(account);
        this.repository.save(entity);
        return this.getById(operationId);
    }

    @Override
    public void delete(UUID accountId, UUID operationId, LocalDateTime dtUpdate) {
        OperationEntity entity = this.repository.findById(operationId)
                .orElseThrow(() -> new IllegalArgumentException("Unable to find operation with ID: " + operationId));
        if (!entity.getDtUpdate().truncatedTo(ChronoUnit.MILLIS).isEqual(dtUpdate)) {
            throw new IllegalArgumentException("Invalid dtUpdate value for this operation");
        } else if (!entity.getAccount().getId().equals(accountId)) {
            throw new IllegalArgumentException("Invalid account value for this operation");
        }
        this.accountService.updateBalance(accountId,
                entity.getValue().negate());
        this.repository.deleteById(operationId);
    }


    @Override
    public Operation getById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID can't be null");
        }
        return this.mapper.map(this.repository.getById(id), Operation.class);
    }
}
