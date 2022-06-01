package by.itacademy.account.dao;

import by.itacademy.account.entities.AccountEntity;
import by.itacademy.account.entities.OperationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OperationRepository extends JpaRepository<OperationEntity, UUID> {

    Page<OperationEntity> findAllByAccount(AccountEntity account, Pageable pageable);
}
