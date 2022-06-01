package by.itacademy.report.services.handler.api;



import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.Map;

public interface IReportHandler<T> {

    ByteArrayOutputStream handle(Map<String, Object> params);

    void createHeaders();

    void createData(Collection<T> data);
}
