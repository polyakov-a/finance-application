package by.itacademy.account.scheduler.services.scheduled_operation;

import by.itacademy.account.scheduler.dao.ScheduledOperationRepository;
import by.itacademy.account.scheduler.dto.PageOfScheduledOperation;
import by.itacademy.account.scheduler.dto.ScheduledOperation;
import by.itacademy.account.scheduler.entities.OperationEntity;
import by.itacademy.account.scheduler.entities.ScheduleEntity;
import by.itacademy.account.scheduler.entities.ScheduledOperationEntity;
import by.itacademy.account.scheduler.services.scheduled_operation.api.IScheduledOperationService;
import by.itacademy.account.scheduler.services.scheduler.SchedulerService;
import by.itacademy.account.scheduler.services.validation.ScheduledOperationValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ScheduledOperationService implements IScheduledOperationService {

    private final ScheduledOperationRepository repository;
    private final ScheduledOperationValidationService validationService;
    private final SchedulerService schedulerService;
    private final ModelMapper mapper;

    public ScheduledOperationService(ScheduledOperationRepository repository,
                                     ScheduledOperationValidationService validationService,
                                     SchedulerService schedulerService,
                                     ModelMapper mapper) {
        this.repository = repository;
        this.schedulerService = schedulerService;
        this.mapper = mapper;
        this.validationService = validationService;
    }

    @Override
    @Transactional
    public ScheduledOperation create(ScheduledOperation scheduledOperation) {
        scheduledOperation = this.validationService.validate(scheduledOperation);
        OperationEntity operationEntity = this.mapper.map(scheduledOperation.getOperation(), OperationEntity.class);
        LocalDateTime now = LocalDateTime.now();
        operationEntity.setDtCreate(now);
        operationEntity.setDtUpdate(now);
        ScheduleEntity scheduleEntity = this.mapper.map(scheduledOperation.getSchedule(), ScheduleEntity.class);
        scheduleEntity.setDtCreate(now);
        scheduleEntity.setDtUpdate(now);
        ScheduledOperationEntity entity = this.mapper.map(scheduledOperation, ScheduledOperationEntity.class);
        entity.setOperation(operationEntity);
        entity.setSchedule(scheduleEntity);
        entity.setDtCreate(now);
        entity.setDtUpdate(now);
        ScheduledOperation saved = this.mapper.map(this.repository.save(entity), ScheduledOperation.class);
        this.schedulerService.addScheduledOperation(saved.getSchedule(), saved.getId());

        return saved;
    }

    @Override
    public PageOfScheduledOperation getAll(Pageable pageable, List<UUID> accounts) {
        Page<ScheduledOperationEntity> result = this.repository.findAll(pageable);

        int totalPages = result.getTotalPages();
        long totalElements = result.getTotalElements();
        boolean first = result.isFirst();
        int numberOfElements = result.getNumberOfElements();
        boolean last = result.isLast();
        List<ScheduledOperation> content = result.getContent()
                .stream()
                .map(entity -> this.mapper.map(entity, ScheduledOperation.class))
                .collect(Collectors.toList());
        if (accounts != null) {
             content = content
                     .stream()
                     .filter(scheduledOperation -> accounts.contains(scheduledOperation.getOperation().getAccount()))
                     .collect(Collectors.toList());

        }

        return PageOfScheduledOperation.of(pageable.getPageNumber(), pageable.getPageSize(), totalPages,
                (int) totalElements, first, numberOfElements, last, content);
    }

    @Override
    public ScheduledOperation update(ScheduledOperation scheduledOperation, UUID id, LocalDateTime dtUpdate) {
        scheduledOperation = this.validationService.validate(scheduledOperation);
        ScheduledOperationEntity entity = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Unable to find operation with ID: " + id));
        if (!entity.getDtUpdate().truncatedTo(ChronoUnit.MILLIS).isEqual(dtUpdate)) {
            throw new IllegalArgumentException("Invalid dtUpdate value for this operation");
        }
        OperationEntity operation = this.mapper.map(scheduledOperation.getOperation(), OperationEntity.class);
        ScheduleEntity schedule = this.mapper.map(scheduledOperation.getSchedule(), ScheduleEntity.class);
        LocalDateTime now = LocalDateTime.now();
        operation.setDtUpdate(now);
        operation.setDtCreate(entity.getOperation().getDtCreate());
        schedule.setDtCreate(entity.getSchedule().getDtCreate());
        schedule.setDtUpdate(now);
        entity.setOperation(operation);
        entity.setSchedule(schedule);
        entity.setDtUpdate(now);
        this.repository.save(entity);

        return this.getById(id);
    }

    @Override
    public ScheduledOperation getById(UUID id) {
        return this.mapper.map(this.repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Unable to find scheduled operation with ID: " + id)), ScheduledOperation.class);
    }
}
