package by.itacademy.classifier.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonPropertyOrder({"id", "dtCreate", "dtUpdate", "title", "description"})
public class Currency extends Essence {

    private String title;
    private String description;

    public Currency() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + super.getId() +
                ", dtCreate=" + super.getDtCreate() +
                ", dtUpdate=" + super.getDtUpdate() +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
