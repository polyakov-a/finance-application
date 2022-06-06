package by.itacademy.classifier.services.operation_category;

import by.itacademy.classifier.dao.OperationCategoryRepository;
import by.itacademy.classifier.dto.OperationCategory;
import by.itacademy.classifier.dto.PageOfClassifier;
import by.itacademy.classifier.entities.OperationCategoryEntity;
import by.itacademy.classifier.services.operation_category.api.IOperationCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class OperationCategoryService implements IOperationCategoryService {

    private final OperationCategoryRepository repository;
    private final OperationCategoryValidationService validationService;
    private final ModelMapper mapper;

    public OperationCategoryService(OperationCategoryRepository repository,
                                    OperationCategoryValidationService validationService,
                                    ModelMapper mapper) {
        this.repository = repository;
        this.validationService = validationService;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public OperationCategory create(OperationCategory operationCategory) {
        operationCategory = this.validationService.validate(operationCategory);
        OperationCategoryEntity entity = this.mapper.map(operationCategory, OperationCategoryEntity.class);
        LocalDateTime now = LocalDateTime.now();
        entity.setDtCreate(now);
        entity.setDtUpdate(now);
        return this.mapper.map(this.repository.save(entity), OperationCategory.class);
    }

    @Override
    public PageOfClassifier<OperationCategory> getAll(Pageable pageable) {
        Page<OperationCategoryEntity> result = this.repository.findAll(pageable);

        int totalPages = result.getTotalPages();
        int totalElements = (int) result.getTotalElements();
        boolean first = result.isFirst();
        int numberOfElements = result.getNumberOfElements();
        boolean last = result.isLast();
        List<OperationCategory> content = result.getContent()
                .stream()
                .map(entity -> this.mapper.map(entity, OperationCategory.class))
                .collect(Collectors.toList());

        return PageOfClassifier.of(pageable.getPageNumber(), pageable.getPageSize(), totalPages,
                totalElements, first, numberOfElements, last, content);
    }

    @Override
    public List<OperationCategory> getAllByIds(List<UUID> ids) {
        return this.repository.findByIdIn(ids)
                .stream()
                .map(entity -> this.mapper.map(entity, OperationCategory.class))
                .collect(Collectors.toList());
    }

    @Override
    public OperationCategory getById(UUID id) {
        return this.mapper.map(
                this.repository.findById(id).orElseThrow(() ->
                        new EntityNotFoundException("Unable to find currency with ID: " + id)), OperationCategory.class);
    }
}
