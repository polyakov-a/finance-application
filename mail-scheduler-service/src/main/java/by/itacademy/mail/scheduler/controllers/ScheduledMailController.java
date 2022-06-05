package by.itacademy.mail.scheduler.controllers;

import by.itacademy.mail.scheduler.dto.PageOfScheduledMail;
import by.itacademy.mail.scheduler.dto.ScheduledMail;
import by.itacademy.mail.scheduler.dto.wrappers.ScheduledMailWrapper;
import by.itacademy.mail.scheduler.services.api.PageVerifier;
import by.itacademy.mail.scheduler.services.scheduled_mail.api.IScheduledMailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.UUID;

@RestController
@RequestMapping("/scheduler/mail")
public class ScheduledMailController {

    private final IScheduledMailService service;

    public ScheduledMailController(IScheduledMailService service) {
        this.service = service;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ScheduledMail> create(@RequestBody ScheduledMailWrapper wrapper) {

        return new ResponseEntity<>(this.service.create(wrapper), HttpStatus.CREATED);

    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<PageOfScheduledMail> getAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        return ResponseEntity.ok(this.service.getAll(PageVerifier.createPageable(page, size)));
    }

    @PutMapping(
            value = "/{uuid}/dt_update/{dt_update}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<ScheduledMail> update(
            @RequestBody ScheduledMailWrapper wrapper,
            @PathVariable("uuid") UUID scheduledMailId,
            @PathVariable("dt_update") Long dtUpdate) {

        return ResponseEntity.ok(this.service.update(wrapper, scheduledMailId,
                LocalDateTime.ofInstant(Instant.ofEpochMilli(dtUpdate), TimeZone.getDefault().toZoneId())));
    }
}
