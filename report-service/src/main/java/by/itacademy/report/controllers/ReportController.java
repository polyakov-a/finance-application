package by.itacademy.report.controllers;

import by.itacademy.report.dto.report.PageOfReport;
import by.itacademy.report.dto.api.ReportType;
import by.itacademy.report.services.api.PageVerifier;
import by.itacademy.report.services.report.api.IReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/report")
public class ReportController {

    private final IReportService reportService;

    public ReportController(IReportService reportService) {
        this.reportService = reportService;
    }


    @PostMapping(
            value = "/{type}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@PathVariable ReportType type,
                                    @RequestBody Map<String, Object> params) {

        this.reportService.create(type, params);
        return ResponseEntity.ok("Report has been created");
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<PageOfReport> index(@RequestParam(required = false) Integer page,
                                               @RequestParam(required = false) Integer size) {

        return ResponseEntity.ok(this.reportService.getAll(PageVerifier.createPageable(page, size)));
    }

    @GetMapping(value = "/{uuid}/export")
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("uuid") UUID reportId) {

        MediaType mediaType = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + reportId + ".xls")
                .contentType(mediaType)
                .body(this.reportService.read(reportId).toByteArray());
    }

    @RequestMapping(
            value = "/{uuid}/export",
            method = RequestMethod.HEAD,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    private ResponseEntity<?> head(@PathVariable("uuid") UUID reportId) {
        this.reportService.read(reportId);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
}
