package by.itacademy.report.services.rest;

import by.itacademy.report.dto.rest.Operation;
import by.itacademy.report.services.rest.api.IRestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OperationRestService implements IRestService<Operation> {

    @Value("${custom.url.operation.get}")
    private String url;
    private final RestTemplate restTemplate;

    public OperationRestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Operation> getAllByIDs(List<UUID> accountIds) {
        String ids = accountIds
                .stream()
                .map(UUID::toString)
                .collect(Collectors.joining(","));
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        URI uri = UriComponentsBuilder.fromUriString(url).build(map).normalize();
        Operation[] operations = this.restTemplate.getForObject(uri, Operation[].class);
        if (operations == null || operations.length == 0) {
            throw new EntityNotFoundException("There are no operations with these IDs");
        } else {
            return List.of(operations);
        }
    }
}
