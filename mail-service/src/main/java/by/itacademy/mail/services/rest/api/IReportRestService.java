package by.itacademy.mail.services.rest.api;

import by.itacademy.mail.dto.api.ReportType;

import java.util.Map;
import java.util.UUID;

public interface IReportRestService {

    UUID getReportId(Map<String, Object> params, ReportType type);
}
