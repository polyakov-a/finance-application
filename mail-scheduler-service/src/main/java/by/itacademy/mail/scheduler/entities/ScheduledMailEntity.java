package by.itacademy.mail.scheduler.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "scheduled_mail", schema = "app")
public class ScheduledMailEntity extends EssenceEntity {

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule")
    private ScheduleEntity schedule;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "mail")
    private MailEntity mail;

    public ScheduledMailEntity() {
    }

    public ScheduleEntity getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleEntity schedule) {
        this.schedule = schedule;
    }

    public MailEntity getMail() {
        return mail;
    }

    public void setMail(MailEntity mail) {
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
}
