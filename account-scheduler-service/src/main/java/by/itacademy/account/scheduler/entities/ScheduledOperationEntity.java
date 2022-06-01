package by.itacademy.account.scheduler.entities;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "scheduled_operation", schema = "app")
public class ScheduledOperationEntity extends EssenceEntity {

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule")
    private ScheduleEntity schedule;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "operation")
    private OperationEntity operation;

    public ScheduledOperationEntity() {
    }

    public UUID getId() {
        return super.getId();
    }

    public void setId(UUID id) {
        super.setId(id);
    }

    public LocalDateTime getDtCreate() {
        return super.getDtCreate();
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        super.setDtCreate(dtCreate);
    }

    public LocalDateTime getDtUpdate() {
        return super.getDtUpdate();
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        super.setDtUpdate(dtUpdate);
    }

    public ScheduleEntity getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleEntity schedule) {
        this.schedule = schedule;
    }

    public OperationEntity getOperation() {
        return operation;
    }

    public void setOperation(OperationEntity operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "ScheduledOperation{" +
                "id=" + super.getId() +
                "dtCreate=" + super.getDtCreate() +
                "dtUpdate=" + super.getDtUpdate() +
                "schedule=" + schedule +
                ", operation=" + operation +
                '}';
    }
}
