package by.itacademy.mail.services.mail;

import by.itacademy.mail.dao.MailRepository;
import by.itacademy.mail.dto.Mail;
import by.itacademy.mail.dto.MailWrapper;
import by.itacademy.mail.dto.PageOfMail;
import by.itacademy.mail.entities.MailEntity;
import by.itacademy.mail.services.mail.api.IMailService;
import by.itacademy.mail.services.rest.api.IReportDataRestService;
import by.itacademy.mail.services.rest.api.IReportRestService;
import by.itacademy.mail.services.validation.MailValidationService;
import by.itacademy.mail.services.validation.ParamsValidationService;
import com.sun.istack.ByteArrayDataSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MailService implements IMailService {

    @Value("${spring.mail.username}")
    private String from;
    private final MailRepository repository;
    private final ParamsValidationService paramsValidationService;
    private final MailValidationService mailValidationService;
    private final IReportDataRestService reportDataRestService;
    private final IReportRestService reportRestService;
    private final JavaMailSender sender;
    private final ModelMapper mapper;

    public MailService(MailRepository repository,
                       ParamsValidationService paramsValidationService,
                       MailValidationService mailValidationService,
                       IReportDataRestService reportDataRestService,
                       IReportRestService reportRestService,
                       JavaMailSender sender,
                       ModelMapper mapper) {
        this.repository = repository;
        this.paramsValidationService = paramsValidationService;
        this.mailValidationService = mailValidationService;
        this.reportDataRestService = reportDataRestService;
        this.reportRestService = reportRestService;
        this.sender = sender;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Mail create(MailWrapper mailWrapper) {
        this.paramsValidationService.validate(mailWrapper.getParams(), mailWrapper.getType());
        this.mailValidationService.validate(mailWrapper.getMail());
        MailEntity entity = this.mapper.map(mailWrapper.getMail(), MailEntity.class);
        entity.setSendDate(LocalDateTime.now());
        entity.setSubject(mailWrapper.getType().name().toLowerCase() + " report");
        entity.setText("params: " + mailWrapper.getParams().toString());
        entity.setFrom(from);

        MailEntity saved = this.repository.save(entity);
        Mail mail = this.mapper.map(saved, Mail.class);
        UUID reportId = this.reportRestService.getReportId(mailWrapper.getParams(), mailWrapper.getType());
        byte[] attachment = this.reportDataRestService.getDate(reportId);
        this.send(mail, attachment);
        return mail;
    }

    @Override
    public PageOfMail getAll(Pageable pageable) {
        Page<MailEntity> result = this.repository.findAll(pageable);

        int totalPages = result.getTotalPages();
        int totalElements = (int) result.getTotalElements();
        boolean first = result.isFirst();
        int numberOfElements = result.getNumberOfElements();
        boolean last = result.isLast();
        List<Mail> content = result.getContent()
                .stream()
                .map(entity -> this.mapper.map(entity, Mail.class))
                .collect(Collectors.toList());

        return PageOfMail.of(pageable.getPageNumber(), pageable.getPageSize(), totalPages,
                totalElements, first, numberOfElements, last, content);
    }

    @Override
    public void send(Mail mail, byte[] attachment) {
        MimeMessage mimeMessage = this.sender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(mail.getTo());
            messageHelper.setText(mail.getText());
            messageHelper.setSubject(mail.getSubject());
            ByteArrayDataSource bads = new ByteArrayDataSource(attachment,
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            messageHelper.addAttachment("report.xls", bads);
            sender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
