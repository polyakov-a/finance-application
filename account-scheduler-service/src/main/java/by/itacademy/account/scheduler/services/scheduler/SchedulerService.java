package by.itacademy.account.scheduler.services.scheduler;

import by.itacademy.account.scheduler.dto.Schedule;
import by.itacademy.account.scheduler.services.scheduler.api.ISchedulerService;
import org.quartz.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class SchedulerService implements ISchedulerService {

    private final Scheduler scheduler;

    public SchedulerService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }


    @Override
    public void addScheduledOperation(Schedule schedule, UUID scheduledOperationId) {
        JobDetail job = JobBuilder.newJob(OperationJob.class)
                .withIdentity(scheduledOperationId.toString(), "operations")
                .usingJobData("operation", scheduledOperationId.toString())
                .build();

        int interval = schedule.getInterval().intValue();
        LocalDateTime start = schedule.getStartTime();
        LocalDateTime stop = schedule.getStopTime();
        Date startTime = DateBuilder.dateOf(
                start.getHour(), start.getMinute(), start.getSecond(),
                start.getDayOfMonth(), start.getMonthValue(), start.getYear());
        Date stopTime = DateBuilder.dateOf(
                stop.getHour(), stop.getMinute(), start.getSecond(),
                stop.getDayOfMonth(), stop.getMonthValue(), stop.getYear());

        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder
                .newTrigger()
                .withIdentity(scheduledOperationId.toString(), "operations");

        SimpleScheduleBuilder ssb = null;
        CronScheduleBuilder csb = null;
        String expression;

        switch (schedule.getTimeUnit()) {
            case SECOND:
                ssb = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(interval);
                break;
            case MINUTE:
                ssb = SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(interval);
                break;
            case HOUR:
                ssb = SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(interval);
                break;
            case DAY:
                ssb = SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(interval * 24);
                break;
            case WEEK:
                ssb = SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(interval * 168);
                break;
            case MONTH:
                expression = String.format("%d %d %d %d * ?",
                        start.getSecond(),
                        start.getMinute(),
                        start.getHour(),
                        start.getDayOfMonth());
                csb = CronScheduleBuilder.cronSchedule(expression);
                break;
            case YEAR:
                expression = String.format("%d %d %d %d %d ?",
                        start.getSecond(),
                        start.getMinute(),
                        start.getHour(),
                        start.getDayOfMonth(),
                        start.getMonthValue());
                csb = CronScheduleBuilder.cronSchedule(expression);
                break;
        }

        if (ssb != null) {
            triggerBuilder.withSchedule(ssb.repeatForever());
        } else if (csb != null) {
            triggerBuilder.withSchedule(csb);
        }
        Trigger trigger = triggerBuilder
                .startAt(startTime)
                .endAt(stopTime)
                .build();

        try {
            this.scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException("Error creating a scheduled operation", e);
        }
    }
}
