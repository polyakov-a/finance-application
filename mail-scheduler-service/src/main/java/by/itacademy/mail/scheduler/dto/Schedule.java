package by.itacademy.mail.scheduler.dto;

import by.itacademy.mail.scheduler.dto.api.TimeUnit;
import by.itacademy.mail.scheduler.dto.serializers.CustomDateTimeDeserializer;
import by.itacademy.mail.scheduler.dto.serializers.CustomDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

@JsonPropertyOrder({"startTime", "stopTime", "interval", "timeUnit"})
public class Schedule {

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @JsonProperty("stop_time")
    private LocalDateTime stopTime;

    private Long interval;

    @JsonProperty("time_unit")
    private TimeUnit timeUnit;

    public Schedule() {
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
                "startTime=" + startTime +
                ", stopTime=" + stopTime +
                ", interval=" + interval +
                ", timeUnit=" + timeUnit +
                '}';
    }
}
