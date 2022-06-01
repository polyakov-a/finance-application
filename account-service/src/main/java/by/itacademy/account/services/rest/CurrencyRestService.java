package by.itacademy.account.services.rest;

import by.itacademy.account.services.rest.api.IClassifierRestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
public class CurrencyRestService implements IClassifierRestService {

    @Value("${custom.url.classifier.currency.get}")
    private String url;
    private final RestTemplate restTemplate;

    public CurrencyRestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void checkClassifierById(UUID id) {
        try {
            this.restTemplate.getForObject(url, Object.class, id);
        } catch (HttpClientErrorException e) {
            throw new EntityNotFoundException("Unable to find currency with ID: " + id);
        }
    }
}
