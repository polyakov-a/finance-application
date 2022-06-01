package by.itacademy.classifier.services.operation_category.api;

import by.itacademy.classifier.dto.OperationCategory;
import by.itacademy.classifier.dto.PageOfClassifier;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IOperationCategoryService {

    OperationCategory create(OperationCategory operationCategory);

    PageOfClassifier<OperationCategory> getAll(Pageable pageable);

    List<OperationCategory> getAllByIds(List<UUID> ids);

    OperationCategory getById(UUID id);
}