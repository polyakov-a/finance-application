package by.itacademy.classifier.dao;

import by.itacademy.classifier.entities.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, UUID> {

    List<CurrencyEntity> findByIdIn(List<UUID> listOfIds);
}
