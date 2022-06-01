package by.itacademy.account.services.operation;

import by.itacademy.account.dto.Operation;
import by.itacademy.account.services.api.IValidationService;
import by.itacademy.account.services.api.exceptions.FieldError;
import by.itacademy.account.services.api.exceptions.MultipleError;
import by.itacademy.account.services.rest.CurrencyRestService;
import by.itacademy.account.services.rest.OperationCategoryRestService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OperationValidationService implements IValidationService<Operation> {

    private final CurrencyRestService currencyRestService;
    private final OperationCategoryRestService operationCategoryRestService;

    public OperationValidationService(CurrencyRestService currencyRestService,
                                      OperationCategoryRestService operationCategoryRestService) {
        this.currencyRestService = currencyRestService;
        this.operationCategoryRestService = operationCategoryRestService;
    }


    @Override
    public Operation validate(Operation operation) {
        MultipleError errors = new MultipleError();
        if (operation == null) {
            throw new IllegalArgumentException("Operation can't be null");
        }
        if (operation.getDescription() == null || operation.getDescription().isEmpty()) {
            errors.addError(new FieldError("description", "description can't be null or empty"));
        }
        if (operation.getDate() == null) {
            operation.setDate(LocalDateTime.now());
        }
        if (operation.getValue() == null || operation.getValue().intValue() == 0) {
            errors.addError(new FieldError("value", "value can't be null or 0"));
        }
        if (operation.getCurrency() == null) {
            errors.addError(new FieldError("currency", "currency can't be null"));
        }
        if (operation.getCategory() == null) {
            errors.addError(new FieldError("category", "category can't be null"));
        }
        if (errors.getErrors().size() > 0) {
            throw errors;
        }
        this.currencyRestService.checkClassifierById(operation.getCurrency());
        this.operationCategoryRestService.checkClassifierById(operation.getCategory());
        return operation;
    }
}
