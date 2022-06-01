package by.itacademy.classifier.dao;

import by.itacademy.classifier.entities.OperationCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OperationCategoryRepository extends JpaRepository<OperationCategoryEntity, UUID> {

    List<OperationCategoryEntity> findByIdIn(List<UUID> listOfIds);

}
