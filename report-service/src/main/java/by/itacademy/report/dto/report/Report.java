package by.itacademy.report.dto.report;


import by.itacademy.report.dto.api.ReportStatus;
import by.itacademy.report.dto.api.ReportType;


public class Report extends Essence {

    private ReportStatus status;
    private ReportType type;
    private String description;
    private String params;

    public Report() {
    }

    private Report(ReportStatus status,
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

    @Override
    public String toString() {
        return "Report{" +
                "status=" + status +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", params=" + params +
                '}';
    }

}
