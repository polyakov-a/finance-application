package by.itacademy.mail.scheduler.services.validation;

import by.itacademy.mail.scheduler.dto.api.ReportType;
import by.itacademy.mail.scheduler.services.api.exceptions.FieldError;
import by.itacademy.mail.scheduler.services.api.exceptions.MultipleError;
import by.itacademy.mail.scheduler.services.validation.api.IParamsValidationService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Service
public class ParamsValidationService implements IParamsValidationService<Map<String, Object>, ReportType> {

    @Override
    public Map<String, Object> validate(Map<String, Object> params, ReportType type) {
        MultipleError errors = new MultipleError();
        if (type == null) {
            throw new IllegalArgumentException("report type can't be null");
        }
        if (params == null) {
            throw new IllegalArgumentException("params can't be null");
        }
        if (params.get("accounts") == null) {
            errors.addError(new FieldError("accounts", "accounts can't be null"));
        } else {
            if (!(params.get("accounts") instanceof Collection)) {
                errors.addError(new FieldError("accounts", "invalid account type"));
            } else {
                if (((Collection<?>) params.get("accounts")).size() == 0) {
                    errors.addError(new FieldError("accounts", "accounts can't be empty"));
                } else {
                    boolean invalid = ((Collection<?>) params.get("accounts"))
                            .stream()
                            .anyMatch(e -> !(e instanceof String));
                    if (invalid) {
                        errors.addError(new FieldError("accounts", "invalid accounts data type"));
                    }
                }
            }
        }
        if (type.equals(ReportType.BY_DATE) || type.equals(ReportType.BY_CATEGORY)) {
            if (params.get("categories") == null) {
                errors.addError(new FieldError("categories", "accounts can't be null"));
            } else {
                if (!(params.get("categories") instanceof Collection)) {
                    errors.addError(new FieldError("categories", "invalid categories type"));
                } else {
                    if (((Collection<?>) params.get("categories")).size() == 0) {
                        errors.addError(new FieldError("accounts", "categories can't be empty"));
                    } else {
                        boolean invalid = ((Collection<?>) params.get("categories"))
                                .stream()
                                .anyMatch(e -> !(e instanceof String));
                        if (invalid) {
                            errors.addError(new FieldError("categories", "invalid categories data type"));
                        }
                    }
                }
            }
        }
        if (errors.getErrors().size() > 0) {
            throw errors;
        }
        return params;
    }
}
