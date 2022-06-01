package by.itacademy.classifier.services.operation_category;

import by.itacademy.classifier.dto.OperationCategory;
import by.itacademy.classifier.services.api.IValidationService;
import by.itacademy.classifier.services.api.exceptions.FieldError;
import by.itacademy.classifier.services.api.exceptions.MultipleError;
import org.springframework.stereotype.Service;

@Service
public class OperationCategoryValidationService implements IValidationService<OperationCategory> {

    @Override
    public OperationCategory validate(OperationCategory operationCategory) {
        MultipleError errors = new MultipleError();
        if (operationCategory == null) {
            throw new IllegalArgumentException("operation category can't be null");
        }
        if (operationCategory.getTitle() == null || operationCategory.getTitle().isEmpty()) {
            errors.addError(new FieldError("title", "title can't be null or empty"));
        }
        if (errors.getErrors().size() > 0) {
            throw errors;
        }
        return operationCategory;
    }
}
