package by.itacademy.classifier.services.currency.api;

import by.itacademy.classifier.dto.Currency;
import by.itacademy.classifier.dto.PageOfClassifier;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ICurrencyService {

    Currency create(Currency currency);

    PageOfClassifier<Currency> getAll(Pageable pageable);

    List<Currency> getAllByIds(List<UUID> ids);

    Currency getById(UUID id);
}
