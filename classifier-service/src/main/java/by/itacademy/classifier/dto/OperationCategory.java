package by.itacademy.classifier.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonPropertyOrder({"id", "dtCreate", "dtUpdate", "title"})
public class OperationCategory extends Essence {

    private String title;

    public OperationCategory() {
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

    @Override
    public String toString() {
        return "OperationCategory{" +
                "id=" + super.getId() +
                ", dtCreate=" + super.getDtCreate() +
                ", dtUpdate=" + super.getDtUpdate() +
                ", title='" + title + '\'' +
                '}';
    }
}
