package by.itacademy.report.dto.report;

import java.util.Arrays;
import java.util.UUID;


public class ReportData {

    private UUID reportId;
    private byte[] data;

    public ReportData() {
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

    @Override
    public String toString() {
        return "ReportData{" +
                "reportId=" + reportId +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
