package by.itacademy.account.scheduler.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonPropertyOrder({"id", "dtCreate", "dtUpdate", "schedule", "operation"})
public class ScheduledOperation extends Essence {

    private Schedule schedule;
    private Operation operation;

    public ScheduledOperation() {
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

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
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
