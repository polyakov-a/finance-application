package by.itacademy.mail.scheduler.services.validation.api;


public interface IParamsValidationService<T, X> {

    T validate(T params, X type);
}
