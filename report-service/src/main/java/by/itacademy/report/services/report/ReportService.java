package by.itacademy.report.services.report;


import by.itacademy.report.dao.ReportRepository;
import by.itacademy.report.dto.report.PageOfReport;
import by.itacademy.report.dto.report.Report;
import by.itacademy.report.dto.api.ReportStatus;
import by.itacademy.report.dto.api.ReportType;
import by.itacademy.report.entities.ReportEntity;
import by.itacademy.report.services.handler.api.IReportHandler;
import by.itacademy.report.services.report.api.IReportService;
import by.itacademy.report.services.handler.api.ReportFactory;
import by.itacademy.report.services.validation.ParamsValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
public class ReportService implements IReportService {

    private final ReportFactory reportFactory;
    private final ReportRepository repository;
    private final ReportDataService reportDataService;
    private final ParamsValidationService paramsValidationService;
    private final ModelMapper mapper;

    public ReportService(ReportFactory reportFactory,
                         ReportRepository repository,
                         ReportDataService reportDataService,
                         ParamsValidationService paramsValidationService,
                         ModelMapper mapper) {
        this.reportFactory = reportFactory;
        this.repository = repository;
        this.reportDataService = reportDataService;
        this.paramsValidationService = paramsValidationService;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public UUID create(ReportType type, Map<String, Object> params) {
        params = this.paramsValidationService.validate(params, type);
        ReportEntity entity = new ReportEntity();
        LocalDateTime now = LocalDateTime.now();
        entity.setDtCreate(now);
        entity.setDtUpdate(now);
        entity.setDescription("Дата создания отчёта " + type.name() + ": " + now.truncatedTo(ChronoUnit.SECONDS));
        entity.setStatus(ReportStatus.PROGRESS);
        entity.setType(type);
        entity.setParams(params.toString());
        ReportEntity saved = this.repository.save(entity);

        ByteArrayOutputStream baos = null;
        try {
            IReportHandler<?> reportHandler = this.reportFactory.chooseReportRealization(type);
            baos = reportHandler.handle(params);
            saved.setStatus(ReportStatus.LOADED);
            saved = this.repository.save(saved);
        } catch (Exception e) {
            saved.setStatus(ReportStatus.ERROR);
        }
        if (baos != null) {
            try {
                this.reportDataService.create(saved.getId(), baos.toByteArray());
                saved.setStatus(ReportStatus.DONE);
            } catch (Exception e) {
                saved.setStatus(ReportStatus.ERROR);
            }
        }
        ReportEntity result = this.repository.save(saved);
        return result.getId();
    }

    @Override
    public ByteArrayOutputStream read(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID can't be null");
        }
        ReportEntity report = this.repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Unable to find report with ID: " + id));
        byte[] data = this.reportDataService.read(id);
        ReportStatus status = report.getStatus();
        if (!this.isAvailable(id)) {
            throw new IllegalArgumentException("Unable to get data from report with status " + status.name());
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream(data.length);
        baos.write(data, 0, data.length);
        return baos;
    }

    @Override
    public PageOfReport getAll(Pageable pageable) {
        Page<ReportEntity> result = this.repository.findAll(pageable);

        int totalPages = result.getTotalPages();
        int totalElements = (int) result.getTotalElements();
        boolean first = result.isFirst();
        int numberOfElements = result.getNumberOfElements();
        boolean last = result.isLast();
        List<Report> content = result.getContent()
                .stream()
                .map(entity -> this.mapper.map(entity, Report.class))
                .collect(Collectors.toList());

        return PageOfReport.of(pageable.getPageNumber(), pageable.getPageSize(), totalPages,
                totalElements, first, numberOfElements, last, content);
    }

    @Override
    public boolean isAvailable(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID can't be null");
        }
        ReportEntity report = this.repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Unable to find report with ID: " + id));
        return report.getStatus().equals(ReportStatus.DONE);
    }
}
