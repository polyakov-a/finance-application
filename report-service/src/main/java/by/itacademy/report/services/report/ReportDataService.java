package by.itacademy.report.services.report;

import by.itacademy.report.dao.ReportDataRepository;
import by.itacademy.report.dto.report.ReportData;
import by.itacademy.report.entities.ReportDataEntity;
import by.itacademy.report.services.report.api.IReportDataService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ReportDataService implements IReportDataService {

    private final ReportDataRepository repository;
    private final ModelMapper mapper;

    public ReportDataService(ReportDataRepository repository,
                             ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public ReportData create(UUID id, byte[] data) {
        if (id == null || data.length == 0) {
            throw new IllegalArgumentException("Invalid parameters");
        }
        ReportData reportData = new ReportData();
        reportData.setReportId(id);
        reportData.setData(data);
        ReportDataEntity entity = this.repository.save(this.mapper.map(reportData, ReportDataEntity.class));
        return this.mapper.map(entity, ReportData.class);
    }

    @Override
    public byte[] read(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID can't be null");
        }
        ReportDataEntity entity = this.repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find report with ID: " + id));
        return entity.getData();
    }
}
