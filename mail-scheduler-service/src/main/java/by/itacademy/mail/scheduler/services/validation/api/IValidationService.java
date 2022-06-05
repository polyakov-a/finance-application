package by.itacademy.mail.scheduler.services.validation.api;


public interface IValidationService<T> {

    T validate(T obj);
}
