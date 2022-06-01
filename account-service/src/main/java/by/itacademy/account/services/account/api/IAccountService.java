package by.itacademy.account.services.account.api;

import by.itacademy.account.dto.Account;
import by.itacademy.account.dto.PageOfAccount;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IAccountService {

    Account create(Account account);

    Account getById(UUID id);

    PageOfAccount getAll(Pageable pageable);

    List<Account> getAll(List<UUID> ids);

    List<Account> getAll();

    Account update(Account account, UUID id, LocalDateTime dtUpdate);

    Account updateBalance(UUID id, BigDecimal value);
}

