package by.itacademy.report.services.handler.api;

import by.itacademy.report.dto.api.ReportType;
import by.itacademy.report.services.handler.BalanceReportHandler;
import by.itacademy.report.services.handler.CategoryReportHandler;
import by.itacademy.report.services.handler.DateReportHandler;
import org.springframework.stereotype.Component;

@Component
public class ReportFactory {

    private final BalanceReportHandler balanceReportHandler;
    private final CategoryReportHandler categoryReportHandler;
    private final DateReportHandler dateReportHandler;

    public ReportFactory(BalanceReportHandler balanceReportHandler,
                         CategoryReportHandler categoryReportHandler,
                         DateReportHandler dateReportHandler) {
        this.balanceReportHandler = balanceReportHandler;
        this.categoryReportHandler = categoryReportHandler;
        this.dateReportHandler = dateReportHandler;
    }

    public IReportHandler<?> chooseReportRealization(ReportType reportType) {
        switch (reportType) {
            case BALANCE:
                return this.balanceReportHandler;
            case BY_CATEGORY:
                return this.categoryReportHandler;
            case BY_DATE:
                return this.dateReportHandler;
            default:
                throw new IllegalArgumentException("Invalid report type value");
        }
    }
}
