package by.itacademy.classifier.services.currency;

import by.itacademy.classifier.dto.Currency;
import by.itacademy.classifier.services.api.IValidationService;
import by.itacademy.classifier.services.api.exceptions.FieldError;
import by.itacademy.classifier.services.api.exceptions.MultipleError;
import org.springframework.stereotype.Service;

@Service
public class CurrencyValidationService implements IValidationService<Currency> {

    @Override
    public Currency validate(Currency currency) {
        MultipleError errors = new MultipleError();
        if (currency == null) {
            throw new IllegalArgumentException("currency can't be null");
        }
        if (nullOrEmpty(currency.getTitle())) {
            errors.addError(new FieldError("title", "title can't be null or empty"));
        }
        if (nullOrEmpty(currency.getDescription())) {
            errors.addError(new FieldError("description", "description can't be null or empty"));
        }
        if (errors.getErrors().size() > 0) {
            throw errors;
        }
        return currency;
    }

    public boolean nullOrEmpty(String field) {
        return field == null || field.isEmpty();
    }
}
