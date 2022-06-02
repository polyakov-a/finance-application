package by.itacademy.mail.services.api.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"cause", "stackTrace", "message", "suppressed", "localizedMessage"})
public class MultipleError extends IllegalArgumentException {

    private String logref;
    private List<FieldError> errors;

    public MultipleError() {
        logref = "structured_error";
        errors = new ArrayList<>();
    }

    public MultipleError(String logref, List<FieldError> errors) {
        this.logref = logref;
        this.errors = errors;
    }

    public String getLogref() {
        return logref;
    }

    public List<FieldError> getErrors() {
        return errors;
    }

    public void addError(FieldError error) {
        this.errors.add(error);
    }
}
