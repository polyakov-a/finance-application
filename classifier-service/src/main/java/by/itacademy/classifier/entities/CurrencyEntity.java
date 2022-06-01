package by.itacademy.classifier.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "currency", schema = "app")
public class CurrencyEntity extends EssenceEntity {

    private String title;
    private String description;

    public CurrencyEntity() {
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
        return "CurrencyEntity{" +
                "id=" + super.getId() +
                ", dtCreate=" + super.getDtCreate() +
                ", dtUpdate=" + super.getDtUpdate() +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
