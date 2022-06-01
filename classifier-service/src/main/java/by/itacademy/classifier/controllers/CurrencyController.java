package by.itacademy.classifier.controllers;

import by.itacademy.classifier.dto.Currency;
import by.itacademy.classifier.dto.PageOfClassifier;
import by.itacademy.classifier.services.currency.api.ICurrencyService;
import by.itacademy.classifier.services.api.PageVerifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/classifier/currency")
public class CurrencyController {

    private final ICurrencyService currencyService;

    public CurrencyController(ICurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Currency create(@RequestBody Currency currency) {

        return this.currencyService.create(currency);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<PageOfClassifier<Currency>> getAll(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {

        return ResponseEntity.ok(this.currencyService.getAll(PageVerifier.createPageable(page, size)));
    }

    @GetMapping(
            value = "/{uuid}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Currency> getById(@PathVariable("uuid") UUID id) {

        return ResponseEntity.ok(currencyService.getById(id));
    }
}
