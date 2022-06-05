package by.itacademy.mail.scheduler.entities;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "mail", schema = "app")
public class MailEntity extends EssenceEntity {

    @Column(name = "\"to\"")
    private String to;
    private String text;
    private String subject;

    @Column(name = "dt_send")
    private LocalDateTime sendDate;

    public MailEntity() {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDateTime sendDate) {
        this.sendDate = sendDate;
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
