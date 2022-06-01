package by.itacademy.report.services.report.api;


import by.itacademy.report.dto.report.ReportData;

import java.util.UUID;

public interface IReportDataService {

    ReportData create(UUID id, byte[] data);

    byte[] read(UUID id);
}
