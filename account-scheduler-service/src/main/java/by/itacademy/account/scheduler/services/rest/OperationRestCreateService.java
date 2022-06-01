package by.itacademy.account.scheduler.services.rest;

import by.itacademy.account.scheduler.services.rest.api.IRestCreateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class OperationRestCreateService implements IRestCreateService<UUID> {

    @Value("${custom.url.operation.create}")
    private String url;
    private final RestTemplate restTemplate;

    public OperationRestCreateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void create(Object operation, UUID id) {

        this.restTemplate.postForObject(url, operation, Object.class, id);
    }
}
