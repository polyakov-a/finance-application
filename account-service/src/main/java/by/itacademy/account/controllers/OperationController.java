package by.itacademy.account.controllers;

import by.itacademy.account.dto.Operation;
import by.itacademy.account.dto.PageOfOperation;
import by.itacademy.account.services.api.PageVerifier;
import by.itacademy.account.services.operation.api.IOperationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.TimeZone;
import java.util.UUID;

@RestController
@RequestMapping("/account/{uuid}/operation")
public class OperationController {

    private final IOperationService operationService;

    public OperationController(IOperationService operationService) {
        this.operationService = operationService;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Operation> create(@RequestBody Operation operation,
                                            @PathVariable("uuid") UUID accountId) {

        return new ResponseEntity<>(this.operationService.create(operation, accountId), HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<PageOfOperation> getAllById(@PathVariable UUID uuid,
                                                      @RequestParam(value = "page", required = false) Integer page,
                                                      @RequestParam(value = "size", required = false) Integer size) {

        return ResponseEntity.ok(
                this.operationService.getAllByAccountId(uuid, PageVerifier.createPageable(page, size)));
    }

    @PutMapping(
            value = "/{uuid_operation}/dt_update/{dt_update}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<Operation> update(@RequestBody Operation operation,
                                            @PathVariable("uuid") UUID accountId,
                                            @PathVariable("uuid_operation") UUID operationId,
                                            @PathVariable("dt_update") Long dtUpdate) {

        return ResponseEntity.ok(this.operationService.update(
                operation, accountId, operationId,
                LocalDateTime.ofInstant(Instant.ofEpochMilli(dtUpdate), TimeZone.getDefault().toZoneId())));
    }

    @DeleteMapping(value = "/{uuid_operation}/dt_update/{dt_update}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable("uuid") UUID accountId,
                                         @PathVariable("uuid_operation") UUID operationId,
                                         @PathVariable("dt_update") Long dtUpdate) {

        this.operationService.delete(
                accountId, operationId,
                LocalDateTime.ofInstant(Instant.ofEpochMilli(dtUpdate), TimeZone.getDefault().toZoneId()));

        return ResponseEntity.ok("Operation has been successfully deleted!");
    }


}
