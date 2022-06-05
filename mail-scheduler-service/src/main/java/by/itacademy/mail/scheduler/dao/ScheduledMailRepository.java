package by.itacademy.mail.scheduler.dao;

import by.itacademy.mail.scheduler.entities.ScheduledMailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScheduledMailRepository extends JpaRepository<ScheduledMailEntity, UUID> {
}
