package by.itacademy.account.scheduler.controllers;

import by.itacademy.account.scheduler.dto.PageOfScheduledOperation;
import by.itacademy.account.scheduler.dto.ScheduledOperation;
import by.itacademy.account.scheduler.services.api.PageVerifier;
import by.itacademy.account.scheduler.services.scheduled_operation.api.IScheduledOperationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

@RestController
@RequestMapping(value = "/sсheduler/operation")
public class ScheduledOperationController {

    private final IScheduledOperationService service;

    public ScheduledOperationController(IScheduledOperationService service) {
        this.service = service;
    }


    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ScheduledOperation> create(@RequestBody ScheduledOperation operation) {

        return new ResponseEntity<>(this.service.create(operation), HttpStatus.CREATED);

    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<PageOfScheduledOperation> getAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) List<UUID> accounts) {

        return ResponseEntity.ok(this.service.getAll(PageVerifier.createPageable(page, size), accounts));
    }

    @PutMapping(
            value = "/{uuid}/dt_update/{dt_update}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<ScheduledOperation> update(
            @RequestBody ScheduledOperation scheduledOperation,
            @PathVariable("uuid") UUID operationId,
            @PathVariable("dt_update") Long dtUpdate) {

        return ResponseEntity.ok(this.service.update(scheduledOperation, operationId,
                LocalDateTime.ofInstant(Instant.ofEpochMilli(dtUpdate), TimeZone.getDefault().toZoneId())));
    }
}
