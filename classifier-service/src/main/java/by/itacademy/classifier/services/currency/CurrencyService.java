package by.itacademy.classifier.services.currency;

import by.itacademy.classifier.dao.CurrencyRepository;
import by.itacademy.classifier.dto.Currency;
import by.itacademy.classifier.dto.PageOfClassifier;
import by.itacademy.classifier.entities.CurrencyEntity;
import by.itacademy.classifier.services.currency.api.ICurrencyService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CurrencyService implements ICurrencyService {

    private final CurrencyRepository repository;
    private final CurrencyValidationService validationService;
    private final ModelMapper mapper;

    public CurrencyService(CurrencyRepository repository,
                           CurrencyValidationService validationService,
                           ModelMapper mapper) {
        this.repository = repository;
        this.validationService = validationService;
        this.mapper = mapper;
    }

    @Override
    public Currency create(Currency currency) {
        currency = this.validationService.validate(currency);
        CurrencyEntity entity = this.mapper.map(currency, CurrencyEntity.class);
        LocalDateTime now = LocalDateTime.now();
        entity.setDtCreate(now);
        entity.setDtUpdate(now);
        return this.mapper.map(this.repository.save(entity), Currency.class);
    }

    @Override
    public PageOfClassifier<Currency> getAll(Pageable pageable) {
        Page<CurrencyEntity> result = this.repository.findAll(pageable);

        int totalPages = result.getTotalPages();
        int totalElements = (int) result.getTotalElements();
        boolean first = result.isFirst();
        int numberOfElements = result.getNumberOfElements();
        boolean last = result.isLast();
        List<Currency> content = result.getContent()
                .stream()
                .map(entity -> this.mapper.map(entity, Currency.class))
                .collect(Collectors.toList());

        return PageOfClassifier.of(pageable.getPageNumber(), pageable.getPageSize(), totalPages,
                totalElements, first, numberOfElements, last, content);
    }

    @Override
    public List<Currency> getAllByIds(List<UUID> ids) {
        return this.repository.findByIdIn(ids)
                .stream()
                .map(entity -> this.mapper.map(entity, Currency.class))
                .collect(Collectors.toList());
    }

    @Override
    public Currency getById(UUID id) {
        return this.mapper.map(
                this.repository.findById(id).orElseThrow(() ->
                        new EntityNotFoundException("Unable to find currency with ID: " + id)), Currency.class);
    }
}
