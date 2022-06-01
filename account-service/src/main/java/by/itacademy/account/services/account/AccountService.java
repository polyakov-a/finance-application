package by.itacademy.account.services.account;

import by.itacademy.account.dao.AccountRepository;
import by.itacademy.account.dto.Account;
import by.itacademy.account.dto.PageOfAccount;
import by.itacademy.account.entities.AccountEntity;
import by.itacademy.account.services.account.api.IAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService implements IAccountService {

    private final AccountRepository repository;
    private final AccountValidationService validationService;
    private final ModelMapper mapper;

    public AccountService(AccountRepository repository,
                          AccountValidationService validationService,
                          ModelMapper mapper) {
        this.repository = repository;
        this.validationService = validationService;
        this.mapper = mapper;
    }

    @Override
    public Account create(Account account) {
        account = this.validationService.validate(account);
        AccountEntity entity = this.mapper.map(account, AccountEntity.class);
        LocalDateTime now = LocalDateTime.now();
        entity.setDtCreate(now);
        entity.setDtUpdate(now);
        return this.mapper.map(this.repository.save(entity), Account.class);
    }

    @Override
    public Account getById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID can't be null");
        }
        return this.mapper.map((this.repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Unable to find account with ID: " + id))), Account.class);
    }

    @Override
    public PageOfAccount getAll(Pageable pageable) {
        Page<AccountEntity> result = this.repository.findAll(pageable);

        int totalPages = result.getTotalPages();
        int totalElements = (int) result.getTotalElements();
        boolean first = result.isFirst();
        int numberOfElements = result.getNumberOfElements();
        boolean last = result.isLast();
        List<Account> content = result.getContent()
                .stream()
                .map(entity -> this.mapper.map(entity, Account.class))
                .collect(Collectors.toList());

        return PageOfAccount.of(pageable.getPageNumber(), pageable.getPageSize(), totalPages,
                totalElements, first, numberOfElements, last, content);
    }

    @Override
    public List<Account> getAll(List<UUID> ids) {
        return this.repository.findByIdIn(ids)
                .stream()
                .map(entity -> this.mapper.map(entity, Account.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> getAll() {
        return this.repository.findAll()
                .stream()
                .map(entity -> this.mapper.map(entity, Account.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Account update(Account account, UUID id, LocalDateTime dtUpdate) {
        account = this.validationService.validate(account);
        if (id == null || dtUpdate == null) {
            throw new IllegalArgumentException("Invalid required parameters have been passed");
        }
        AccountEntity entity = this.repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find account with ID: " + id));
        if (!entity.getDtUpdate().truncatedTo(ChronoUnit.MILLIS).isEqual(dtUpdate)) {
            throw new IllegalArgumentException("Invalid dtUpdate value for the account with this ID");
        } else {
            entity.setTitle(account.getTitle());
            entity.setDescription(account.getDescription());
            entity.setType(account.getType());
            entity.setCurrency(account.getCurrency());
            entity.setDtUpdate(LocalDateTime.now());
            this.repository.save(entity);
        }
        return this.getById(id);
    }

    @Override
    @Transactional
    public Account updateBalance(UUID accountId, BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("Value can't be null");
        }
        AccountEntity entity = this.repository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find account with ID: " + accountId));
        entity.setBalance(entity.getBalance().add(value));
        entity.setDtCreate(LocalDateTime.now());
        this.repository.save(entity);
        return this.getById(accountId);
    }
}
