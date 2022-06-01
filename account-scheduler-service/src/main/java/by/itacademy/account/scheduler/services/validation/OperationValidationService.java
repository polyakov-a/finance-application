package by.itacademy.account.scheduler.services.validation;

import by.itacademy.account.scheduler.dto.Operation;
import by.itacademy.account.scheduler.services.api.IValidationService;
import by.itacademy.account.scheduler.services.api.exceptions.FieldError;
import by.itacademy.account.scheduler.services.api.exceptions.MultipleError;
import by.itacademy.account.scheduler.services.rest.AccountRestService;
import by.itacademy.account.scheduler.services.rest.CurrencyRestService;
import by.itacademy.account.scheduler.services.rest.OperationCategoryRestService;
import org.springframework.stereotype.Service;

@Service
public class OperationValidationService implements IValidationService<Operation> {

    private final AccountRestService accountRestService;
    private final CurrencyRestService currencyRestService;
    private final OperationCategoryRestService categoryRestService;

    public OperationValidationService(AccountRestService accountRestService,
                                      CurrencyRestService currencyRestService,
                                      OperationCategoryRestService categoryRestService) {
        this.accountRestService = accountRestService;
        this.currencyRestService = currencyRestService;
        this.categoryRestService = categoryRestService;
    }

    @Override
    public Operation validate(Operation operation) {
        if (operation == null) {
            throw new IllegalArgumentException("Operation can't be null");
        }
        MultipleError errors = new MultipleError();
        if (operation.getDescription() == null) {
            errors.addError(new FieldError("description", "description can't be null"));
        }
        if (operation.getValue() == null) {
            errors.addError(new FieldError("value", "value can't be null"));
        }
        if (operation.getCategory() == null) {
            errors.addError(new FieldError("category", "category can't be null"));
        }
        if (operation.getCurrency() == null) {
            errors.addError(new FieldError("currency", "currency can't be null"));

        }
        if (operation.getAccount() == null) {
            errors.addError(new FieldError("account", "account can't be null"));
        }
        this.categoryRestService.checkById(operation.getCategory());
        this.currencyRestService.checkById(operation.getCurrency());
        this.accountRestService.checkById(operation.getAccount());
        if (errors.getErrors().size() > 0) {
            throw errors;
        }
        return operation;
    }
}
