package by.itacademy.mail.services.validation.api;

public interface IValidationService<T> {

    T validate(T obj);
}
