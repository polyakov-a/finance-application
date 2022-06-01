package by.itacademy.classifier.controllers.backend;

import by.itacademy.classifier.services.currency.api.ICurrencyService;
import by.itacademy.classifier.services.operation_category.api.IOperationCategoryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/backend/classifier")
public class BackendController {

    private final ICurrencyService currencyService;
    private final IOperationCategoryService operationCategoryService;

    public BackendController(ICurrencyService currencyService,
                             IOperationCategoryService operationCategoryService) {
        this.currencyService = currencyService;
        this.operationCategoryService = operationCategoryService;
    }

    @GetMapping(
            value = "/currency/{ids}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getAllCurrencies(@PathVariable List<UUID> ids) {

        return ResponseEntity.ok(this.currencyService.getAllByIds(ids));
    }

    @GetMapping(
            value = "/operation/category/{ids}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getAllOperationCategories(@PathVariable List<UUID> ids) {

        return ResponseEntity.ok(this.operationCategoryService.getAllByIds(ids));
    }
}
