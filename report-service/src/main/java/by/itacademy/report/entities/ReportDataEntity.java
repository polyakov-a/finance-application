package by.itacademy.report.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "report_data", schema = "app")
public class ReportDataEntity {

    @Id
    @Column(name = "report_id")
    private UUID reportId;

    private byte[] data;

    public ReportDataEntity() {
    }

    public UUID getReportId() {
        return reportId;
    }

    public void setReportId(UUID reportId) {
        this.reportId = reportId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
