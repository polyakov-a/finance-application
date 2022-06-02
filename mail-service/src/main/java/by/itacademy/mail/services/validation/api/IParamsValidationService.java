package by.itacademy.mail.services.validation.api;


public interface IParamsValidationService<T, X> {

    T validate(T params, X type);
}
