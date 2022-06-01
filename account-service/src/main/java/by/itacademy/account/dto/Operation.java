package by.itacademy.account.dto;

import by.itacademy.account.dto.serializers.CustomDateTimeDeserializer;
import by.itacademy.account.dto.serializers.CustomDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonPropertyOrder({"id", "dtCreate", "dtUpdate", "date", "description", "category", "value", "currency"})
public class Operation extends Essence {

    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private LocalDateTime date;
    private String description;
    private UUID category;
    private BigDecimal value;
    private UUID currency;

    public Operation() {
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getCategory() {
        return category;
    }

    public void setCategory(UUID category) {
        this.category = category;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public UUID getCurrency() {
        return currency;
    }

    public void setCurrency(UUID currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + super.getId() +
                ", dtCreate=" + super.getDtCreate() +
                ", dtUpdate=" + super.getDtUpdate() +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", value=" + value +
                ", currency=" + currency +
                '}';
    }
}
