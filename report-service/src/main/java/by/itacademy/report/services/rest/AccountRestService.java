package by.itacademy.report.services.rest;

import by.itacademy.report.dto.rest.Account;
import by.itacademy.report.services.rest.api.IRestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountRestService implements IRestService<Account> {

    @Value("${custom.url.account.get}")
    private String url;
    private final RestTemplate restTemplate;

    public AccountRestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Account> getAllByIDs(List<UUID> uuids) {
        String ids = uuids
                .stream()
                .map(UUID::toString)
                .collect(Collectors.joining(","));
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        URI uri = UriComponentsBuilder.fromUriString(url).build(map).normalize();
        Account[] accounts = this.restTemplate.getForObject(uri, Account[].class);
        if (accounts == null || accounts.length == 0) {
            throw new EntityNotFoundException("There are no accounts with these IDs");
        } else {
            return List.of(accounts);
        }
    }
}
