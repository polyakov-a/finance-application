package by.itacademy.report.services.rest.api;


import java.util.List;
import java.util.UUID;

public interface IRestService<T> {

    List<T> getAllByIDs(List<UUID> ids);
}
