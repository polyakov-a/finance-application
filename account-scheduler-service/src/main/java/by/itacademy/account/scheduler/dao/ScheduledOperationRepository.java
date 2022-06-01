package by.itacademy.account.scheduler.dao;

import by.itacademy.account.scheduler.entities.ScheduledOperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScheduledOperationRepository extends JpaRepository<ScheduledOperationEntity, UUID> {
}
