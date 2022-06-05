package by.itacademy.mail.scheduler.dto;


import by.itacademy.mail.scheduler.dto.serializers.CustomDateTimeDeserializer;
import by.itacademy.mail.scheduler.dto.serializers.CustomDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

@JsonPropertyOrder({"from", "to", "subject", "text", "sendDate"})
public class Mail {

    private String to;
    private String subject;
    private String text;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @JsonProperty("send_date")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private LocalDateTime sendDate;

    public Mail() {
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
    public String toString() {
        return "Mail{" +
                ", to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", sendDate=" + sendDate +
                '}';
    }
}
