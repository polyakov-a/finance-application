package by.itacademy.report.entities;

import by.itacademy.report.dto.api.ReportStatus;
import by.itacademy.report.dto.api.ReportType;

import javax.persistence.*;

@Entity
@Table(name = "report", schema = "app")
public class ReportEntity extends EssenceEntity {

    @Enumerated(value = EnumType.STRING)
    private ReportStatus status;

    @Enumerated(value = EnumType.STRING)
    private ReportType type;
    private String description;

    private String params;

    public ReportEntity() {
    }

    public ReportEntity(ReportStatus status,
                        ReportType type,
                        String description,
                        String params) {
        this.status = status;
        this.type = type;
        this.description = description;
        this.params = params;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
