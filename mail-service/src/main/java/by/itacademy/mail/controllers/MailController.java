package by.itacademy.mail.controllers;

import by.itacademy.mail.dto.Mail;
import by.itacademy.mail.dto.MailWrapper;
import by.itacademy.mail.dto.PageOfMail;
import by.itacademy.mail.services.api.PageVerifier;
import by.itacademy.mail.services.mail.api.IMailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/mail")
public class MailController {

    private final IMailService mailService;


    public MailController(IMailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Mail> create(@RequestBody MailWrapper mailWrapper) {

        return new ResponseEntity<>(this.mailService.create(mailWrapper), HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<PageOfMail> getAll(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {

        return ResponseEntity.ok(this.mailService.getAll(PageVerifier.createPageable(page, size)));
    }
}
