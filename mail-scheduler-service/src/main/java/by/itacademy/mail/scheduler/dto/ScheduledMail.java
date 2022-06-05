package by.itacademy.mail.scheduler.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.util.UUID;


@JsonPropertyOrder({"id", "dtCreate", "dtUpdate", "schedule", "mail"})
public class ScheduledMail extends Essence {

    private Schedule schedule;
    private Mail mail;

    public ScheduledMail() {
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

    @Override
    public UUID getId() {
        return super.getId();
    }

    @Override
    public void setId(UUID id) {
        super.setId(id);
    }

    @Override
    public LocalDateTime getDtCreate() {
        return super.getDtCreate();
    }

    @Override
    public void setDtCreate(LocalDateTime dtCreate) {
        super.setDtCreate(dtCreate);
    }

    @Override
    public LocalDateTime getDtUpdate() {
        return super.getDtUpdate();
    }

    @Override
    public void setDtUpdate(LocalDateTime dtUpdate) {
        super.setDtUpdate(dtUpdate);
    }

    @Override
    public String toString() {
        return "ScheduledMail{" +
                "schedule=" + schedule +
                ", mail=" + mail +
                '}';
    }
}
