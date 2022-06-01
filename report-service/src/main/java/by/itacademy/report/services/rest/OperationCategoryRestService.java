package by.itacademy.report.services.rest;

import by.itacademy.report.dto.rest.Category;
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
public class OperationCategoryRestService implements IRestService<Category> {

    @Value("${custom.url.classifier.operation-category.get}")
    private String url;
    private final RestTemplate restTemplate;

    public OperationCategoryRestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Category> getAllByIDs(List<UUID> uuids) {
        String ids = uuids
                .stream()
                .map(UUID::toString)
                .collect(Collectors.joining(","));
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        URI uri = UriComponentsBuilder.fromUriString(url).build(map).normalize();
        Category[] categories = this.restTemplate.getForObject(uri, Category[].class);
        if (categories == null || categories.length == 0) {
            throw new EntityNotFoundException("There are no categories with these IDs");
        } else {
            return List.of(categories);
        }
    }
}
