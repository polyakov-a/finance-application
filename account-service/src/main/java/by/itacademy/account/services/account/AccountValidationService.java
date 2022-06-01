package by.itacademy.account.services.account;

import by.itacademy.account.dto.Account;
import by.itacademy.account.services.api.IValidationService;
import by.itacademy.account.services.api.exceptions.FieldError;
import by.itacademy.account.services.api.exceptions.MultipleError;
import by.itacademy.account.services.rest.CurrencyRestService;
import org.springframework.stereotype.Service;

@Service
public class AccountValidationService implements IValidationService<Account> {

    private final CurrencyRestService currencyRestService;

    public AccountValidationService(CurrencyRestService currencyRestService) {
        this.currencyRestService = currencyRestService;
    }

    @Override
    public Account validate(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account can't be null");
        }
        MultipleError errors = new MultipleError();
        if (account.getTitle() == null) {
            errors.addError(new FieldError("title", "title can't be null"));
        }
        if (account.getDescription() == null) {
            errors.addError(new FieldError("description", "description can't be null"));
        }
        if (account.getType() == null) {
            errors.addError(new FieldError("type", "type can't be null"));
        }
        if (account.getCurrency() == null) {
            errors.addError(new FieldError("currency", "currency can't be null or empty"));
        }
        if (errors.getErrors().size() > 0) {
            throw errors;
        }
        this.currencyRestService.checkClassifierById(account.getCurrency());
        return account;
    }
}
