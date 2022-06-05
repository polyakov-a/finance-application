package by.itacademy.mail.scheduler.services.scheduled_mail;

import by.itacademy.mail.scheduler.dao.ScheduledMailRepository;
import by.itacademy.mail.scheduler.dto.wrappers.MailWrapper;
import by.itacademy.mail.scheduler.dto.PageOfScheduledMail;
import by.itacademy.mail.scheduler.dto.ScheduledMail;
import by.itacademy.mail.scheduler.dto.wrappers.ScheduledMailWrapper;
import by.itacademy.mail.scheduler.entities.MailEntity;
import by.itacademy.mail.scheduler.entities.ScheduleEntity;
import by.itacademy.mail.scheduler.entities.ScheduledMailEntity;
import by.itacademy.mail.scheduler.services.scheduled_mail.api.IScheduledMailService;
import by.itacademy.mail.scheduler.services.scheduler.api.ISchedulerService;
import by.itacademy.mail.scheduler.services.validation.MailValidationService;
import by.itacademy.mail.scheduler.services.validation.ParamsValidationService;
import by.itacademy.mail.scheduler.services.validation.ScheduleValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ScheduledMailService implements IScheduledMailService {

    private final ScheduledMailRepository repository;
    private final ISchedulerService schedulerService;
    private final MailValidationService mailValidationService;
    private final ParamsValidationService paramsValidationService;
    private final ScheduleValidationService scheduleValidationService;
    private final ModelMapper mapper;

    public ScheduledMailService(ScheduledMailRepository repository,
                                ISchedulerService schedulerService,
                                MailValidationService mailValidationService,
                                ParamsValidationService paramsValidationService,
                                ScheduleValidationService scheduleValidationService,
                                ModelMapper mapper) {
        this.repository = repository;
        this.schedulerService = schedulerService;
        this.mailValidationService = mailValidationService;
        this.paramsValidationService = paramsValidationService;
        this.scheduleValidationService = scheduleValidationService;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public ScheduledMail create(ScheduledMailWrapper wrapper) {
        this.scheduleValidationService.validate(wrapper.getSchedule());
        this.mailValidationService.validate(wrapper.getMail());
        this.paramsValidationService.validate(wrapper.getParams(), wrapper.getType());
        MailEntity mail = this.mapper.map(wrapper.getMail(), MailEntity.class);
        LocalDateTime now = LocalDateTime.now();
        mail.setDtCreate(now);
        mail.setDtUpdate(now);
        mail.setSendDate(now);
        mail.setSubject(wrapper.getType().name().toLowerCase() + " report");

        Map<String, Object> params = wrapper.getParams();
        long from = now.toLocalDate().atTime(LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long to = now.toLocalDate().atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        params.put("from", from);
        params.put("to", to);

        mail.setText("params: " + params.toString());
        ScheduleEntity schedule = this.mapper.map(wrapper.getSchedule(), ScheduleEntity.class);
        schedule.setDtCreate(now);
        schedule.setDtUpdate(now);
        ScheduledMailEntity entity = new ScheduledMailEntity();
        entity.setMail(mail);
        entity.setSchedule(schedule);
        entity.setDtCreate(now);
        entity.setDtUpdate(now);
        ScheduledMail saved = this.mapper.map(this.repository.save(entity), ScheduledMail.class);

        MailWrapper mailWrapper = new MailWrapper();
        mailWrapper.setMail(saved.getMail());
        mailWrapper.setType(wrapper.getType());
        mailWrapper.setParams(params);
        this.schedulerService.addScheduledMail(saved.getSchedule(), mailWrapper);

        return saved;
    }

    @Override
    public PageOfScheduledMail getAll(Pageable pageable) {
        Page<ScheduledMailEntity> result = this.repository.findAll(pageable);

        int totalPages = result.getTotalPages();
        long totalElements = result.getTotalElements();
        boolean first = result.isFirst();
        int numberOfElements = result.getNumberOfElements();
        boolean last = result.isLast();
        List<ScheduledMail> content = result.getContent()
                .stream()
                .map(entity -> this.mapper.map(entity, ScheduledMail.class))
                .collect(Collectors.toList());

        return PageOfScheduledMail.of(pageable.getPageNumber(), pageable.getPageSize(), totalPages,
                (int) totalElements, first, numberOfElements, last, content);
    }

    @Override
    public ScheduledMail getById(UUID id) {
        return this.mapper.map(this.repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Unable to find scheduled mail with ID: " + id)), ScheduledMail.class);
    }

    @Override
    public ScheduledMail update(ScheduledMailWrapper wrapper, UUID id, LocalDateTime dtUpdate) {
        this.scheduleValidationService.validate(wrapper.getSchedule());
        this.mailValidationService.validate(wrapper.getMail());
        this.paramsValidationService.validate(wrapper.getParams(), wrapper.getType());
        ScheduledMailEntity entity = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Unable to find operation with ID: " + id));
        if (!entity.getDtUpdate().truncatedTo(ChronoUnit.MILLIS).isEqual(dtUpdate)) {
            throw new IllegalArgumentException("Invalid dtUpdate value for this operation");
        }
        MailEntity mail = this.mapper.map(wrapper.getMail(), MailEntity.class);
        ScheduleEntity schedule = this.mapper.map(wrapper.getSchedule(), ScheduleEntity.class);
        LocalDateTime now = LocalDateTime.now();
        mail.setDtUpdate(now);
        mail.setDtCreate(entity.getMail().getDtCreate());
        mail.setSubject(wrapper.getType().name().toLowerCase() + " report");

        Map<String, Object> params = wrapper.getParams();
        long from = now.toLocalDate().atTime(LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long to = now.toLocalDate().atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        params.put("from", from);
        params.put("to", to);

        mail.setText("params: " + wrapper.getParams().toString());
        schedule.setDtCreate(entity.getSchedule().getDtCreate());
        schedule.setDtUpdate(now);
        entity.setMail(mail);
        entity.setSchedule(schedule);
        entity.setDtUpdate(now);
        ScheduledMail saved = this.mapper.map(this.repository.save(entity), ScheduledMail.class);

        MailWrapper mailWrapper = new MailWrapper();
        mailWrapper.setMail(saved.getMail());
        mailWrapper.setType(wrapper.getType());
        mailWrapper.setParams(params);
        this.schedulerService.addScheduledMail(saved.getSchedule(), mailWrapper);

        return this.getById(id);
    }
}
