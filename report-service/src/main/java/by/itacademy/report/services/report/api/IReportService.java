package by.itacademy.report.services.report.api;

import by.itacademy.report.dto.report.PageOfReport;
import by.itacademy.report.dto.api.ReportType;
import org.springframework.data.domain.Pageable;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.UUID;

public interface IReportService {

    void create(ReportType type, Map<String, Object> params);

    ByteArrayOutputStream read(UUID id);

    PageOfReport getAll(Pageable pageable);
}
