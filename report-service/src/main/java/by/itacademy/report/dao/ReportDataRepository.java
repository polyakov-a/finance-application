package by.itacademy.report.dao;

import by.itacademy.report.entities.ReportDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReportDataRepository extends JpaRepository<ReportDataEntity, UUID> {
}
