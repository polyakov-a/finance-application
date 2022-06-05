package by.itacademy.mail.scheduler.services.rest;

import by.itacademy.mail.scheduler.dto.wrappers.MailWrapper;
import by.itacademy.mail.scheduler.services.rest.api.IMailRestCreateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;


@Service
public class MailRestCreateService implements IMailRestCreateService {

    @Value("${custom.url.mail.create}")
    private String url;
    private final RestTemplate restTemplate;

    public MailRestCreateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public void create(MailWrapper mailWrapper) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MailWrapper> entity = new HttpEntity<>(mailWrapper, headers);
        try {
            restTemplate.postForObject(url, entity, String.class);
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
