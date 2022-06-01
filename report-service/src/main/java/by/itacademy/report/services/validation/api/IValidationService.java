package by.itacademy.report.services.validation.api;


public interface IValidationService<T, X> {

    T validate(T params, X type);
}
