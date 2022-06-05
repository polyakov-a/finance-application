package by.itacademy.mail.scheduler.dto.wrappers;

import by.itacademy.mail.scheduler.dto.Mail;
import by.itacademy.mail.scheduler.dto.Schedule;
import by.itacademy.mail.scheduler.dto.api.ReportType;

import java.util.Map;

public class ScheduledMailWrapper {

    private Schedule schedule;
    private Mail mail;
    private ReportType type;
    private Map<String, Object> params;

    public ScheduledMailWrapper() {
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "ScheduledMailWrapper{" +
                "schedule=" + schedule +
                ", mail=" + mail +
                ", type=" + type +
                ", params=" + params +
                '}';
    }
}
