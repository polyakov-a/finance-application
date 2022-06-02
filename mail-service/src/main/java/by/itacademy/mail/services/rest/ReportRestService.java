package by.itacademy.mail.services.rest;

import by.itacademy.mail.dto.api.ReportType;
import by.itacademy.mail.services.rest.api.IReportRestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ReportRestService implements IReportRestService {

    @Value("${custom.url.report.create}")
    private String url;
    private final RestTemplate restTemplate;

    public ReportRestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public UUID getReportId(Map<String, Object> params, ReportType type) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        URI uri = UriComponentsBuilder.fromUriString(url).build(map).normalize();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(params, headers);
        try {
            UUID id = this.restTemplate.postForObject(uri, entity, UUID.class);
            return id;
        } catch (HttpStatusCodeException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
