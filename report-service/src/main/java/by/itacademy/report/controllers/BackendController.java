package by.itacademy.report.controllers;

import by.itacademy.report.dto.api.ReportType;
import by.itacademy.report.services.report.ReportService;
import by.itacademy.report.services.report.api.IReportDataService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/backend/report")
public class BackendController {

    private final IReportDataService reportDataService;
    private final ReportService reportService;

    public BackendController(IReportDataService reportDataService,
                             ReportService reportService) {
        this.reportDataService = reportDataService;
        this.reportService = reportService;
    }

    @GetMapping(value = "/data/{uuid}")
    public byte[] getData(@PathVariable("uuid") UUID reportId) {
        return this.reportDataService.read(reportId);
    }

    @PostMapping(value = "/{type}")
    public UUID create(@PathVariable ReportType type,
                       @RequestBody Map<String, Object> params) {

        return this.reportService.create(type, params);
    }
}
