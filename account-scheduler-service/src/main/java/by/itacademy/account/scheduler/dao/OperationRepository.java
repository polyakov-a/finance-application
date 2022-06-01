package by.itacademy.account.scheduler.dao;

import by.itacademy.account.scheduler.entities.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface OperationRepository extends JpaRepository<OperationEntity, UUID> {
}
