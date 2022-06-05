package by.itacademy.mail.scheduler.services.scheduler;

import by.itacademy.mail.scheduler.dto.wrappers.MailWrapper;
import by.itacademy.mail.scheduler.services.rest.api.IMailRestCreateService;
import org.quartz.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component
@Transactional
public class MailJob implements Job {

    private final IMailRestCreateService mailRestCreateService;

    public MailJob(IMailRestCreateService mailRestCreateService) {
        this.mailRestCreateService = mailRestCreateService;
    }


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        Map<String, Object> wrappedMap = jobDataMap.getWrappedMap();
        MailWrapper mailWrapper = (MailWrapper) wrappedMap.get("mailWrapper");
        this.mailRestCreateService.create(mailWrapper);
    }
}
