package by.itacademy.mail.services.rest;

import by.itacademy.mail.services.rest.api.IReportDataRestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
public class ReportDataRestService implements IReportDataRestService {

    @Value("${custom.url.report.data.get}")
    private String url;
    private final RestTemplate restTemplate;

    public ReportDataRestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public byte[] getDate(UUID reportId) {
        if (reportId == null) {
            throw new IllegalArgumentException("reportId can't be null");
        }
        byte[] data = this.restTemplate.getForObject(url, byte[].class, reportId);
        if (data == null || data.length == 0) {
            throw new EntityNotFoundException("There is no report with ID: " + reportId);
        } else {
            return data;
        }
    }
}
