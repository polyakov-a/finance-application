package by.itacademy.account.controllers;

import by.itacademy.account.dto.Account;
import by.itacademy.account.dto.PageOfAccount;
import by.itacademy.account.services.api.PageVerifier;
import by.itacademy.account.services.account.api.IAccountService;
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
@RequestMapping("/account")
public class AccountController {

    private final IAccountService accountService;

    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Account> create(@RequestBody Account account) {

        return new ResponseEntity<>(this.accountService.create(account), HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<PageOfAccount> getAll(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {

        return ResponseEntity.ok(this.accountService.getAll(PageVerifier.createPageable(page, size)));
    }

    @GetMapping(
            value = "/{uuid}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Account> getById(@PathVariable String uuid) {

        return ResponseEntity.ok(this.accountService.getById(UUID.fromString(uuid)));
    }

    @PutMapping(
            value = "/{uuid}/dt_update/{dt_update}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Account> update(@RequestBody Account account,
                       @PathVariable String uuid,
                       @PathVariable("dt_update") Long dtUpdate) {

        return ResponseEntity.ok(this.accountService.update(
                account, UUID.fromString(uuid),
                LocalDateTime.ofInstant(Instant.ofEpochMilli(dtUpdate), TimeZone.getDefault().toZoneId())));
    }
}
