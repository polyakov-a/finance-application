package by.itacademy.classifier.controllers;

import by.itacademy.classifier.dto.OperationCategory;
import by.itacademy.classifier.dto.PageOfClassifier;
import by.itacademy.classifier.services.operation_category.api.IOperationCategoryService;
import by.itacademy.classifier.services.api.PageVerifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/classifier/operation/category")
public class OperationCategoryController {

    private final IOperationCategoryService operationCategoryService;

    public OperationCategoryController(IOperationCategoryService operationCategoryService) {
        this.operationCategoryService = operationCategoryService;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public OperationCategory create(@RequestBody OperationCategory operationCategory) {

        return this.operationCategoryService.create(operationCategory);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<PageOfClassifier<OperationCategory>> getAll(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {

        return ResponseEntity.ok(this.operationCategoryService.getAll(PageVerifier.createPageable(page, size)));
    }

    @GetMapping(
            value = "/{uuid}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperationCategory> getById(@PathVariable("uuid") UUID id) {

        return ResponseEntity.ok(operationCategoryService.getById(id));
    }
}
