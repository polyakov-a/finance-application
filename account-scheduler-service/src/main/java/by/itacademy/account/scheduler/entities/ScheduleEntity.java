package by.itacademy.account.scheduler.entities;

import by.itacademy.account.scheduler.dto.api.TimeUnit;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "schedule", schema = "app")
public class ScheduleEntity extends EssenceEntity {

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "stop_time")
    private LocalDateTime stopTime;
    private Long interval;

    @Column(name = "time_unit")
    @Enumerated(value = EnumType.STRING)
    private TimeUnit timeUnit;

    public ScheduleEntity() {
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getStopTime() {
        return stopTime;
    }

    public void setStopTime(LocalDateTime stopTime) {
        this.stopTime = stopTime;
    }

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + super.getId() +
                "dtCreate=" + super.getDtCreate() +
                "dtUpdate=" + super.getDtUpdate() +
                "startTime=" + startTime +
                ", stopTime=" + stopTime +
                ", interval=" + interval +
                ", timeUnit=" + timeUnit +
                '}';
    }
}
