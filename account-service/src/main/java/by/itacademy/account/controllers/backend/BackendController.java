package by.itacademy.account.controllers.backend;

import by.itacademy.account.services.account.api.IAccountService;
import by.itacademy.account.services.operation.api.IOperationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/backend")
public class BackendController {

    private final IAccountService accountService;
    private final IOperationService operationService;

    public BackendController(IAccountService accountService,
                             IOperationService operationService) {
        this.accountService = accountService;
        this.operationService = operationService;
    }

    @GetMapping(
            value = "/account/{ids}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getAllAccounts(@PathVariable List<UUID> ids) {
        return ResponseEntity.ok(this.accountService.getAll(ids));
    }

    @GetMapping(
            value = "/operation/{ids}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getAllOperations(@PathVariable("ids") List<UUID> accountIds) {
        return ResponseEntity.ok(this.operationService.getAllByAccountIds(accountIds));
    }
}
