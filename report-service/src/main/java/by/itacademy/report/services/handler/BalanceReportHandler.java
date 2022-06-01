package by.itacademy.report.services.handler;

import by.itacademy.report.dto.rest.Account;
import by.itacademy.report.dto.rest.Currency;
import by.itacademy.report.services.handler.api.IReportHandler;
import by.itacademy.report.services.rest.AccountRestService;
import by.itacademy.report.services.rest.CurrencyRestService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BalanceReportHandler implements IReportHandler<Account> {

    private final AccountRestService accountRestService;
    private final CurrencyRestService currencyRestService;
    private final Workbook workbook;
    private final Sheet sheet;


    public BalanceReportHandler(AccountRestService restService,
                                CurrencyRestService currencyRestService) {
        this.accountRestService = restService;
        this.currencyRestService = currencyRestService;
        this.workbook = new HSSFWorkbook();
        this.sheet = workbook.createSheet("Balance report");
    }

    @Override
    public ByteArrayOutputStream handle(Map<String, Object> params) {
        List<String> raw = (List<String>) params.get("accounts");

        List<UUID> accountIDs = raw
                .stream()
                .map(UUID::fromString)
                .collect(Collectors.toList());
        List<Account> accounts = this.accountRestService.getAllByIDs(accountIDs);
        this.createHeaders();
        this.createData(accounts);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            this.workbook.write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return baos;
    }

    @Override
    public void createHeaders() {
        Row row = this.sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints ((short) 14);
        font.setBold(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        row.setRowStyle(style);

        Cell cell = row.createCell(0);
        cell.setCellValue("balance");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("title");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("description");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("balance");
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("type");
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue("currency");
        cell.setCellStyle(style);
    }

    @Override
    public void createData(Collection<Account> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null");
        }
        List<UUID> currencyIDs = data
                .stream()
                .map(Account::getCurrency)
                .collect(Collectors.toList());
        List<Currency> currencies = this.currencyRestService.getAllByIDs(currencyIDs);
        Map<UUID, String> currencyMap = currencies
                .stream()
                .collect(Collectors.toMap(Currency::getUuid, Currency::getTitle));
        int rowCounter = 1;
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints ((short) 12);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setFont(font);

        for (Account account : data) {
            Row row = this.sheet.createRow(rowCounter);
            Cell cell = row.createCell(0);
            cell.setCellValue(account.getUuid().toString());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(account.getTitle());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(account.getDescription());
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(account.getBalance().toString());
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue(account.getType());
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue(currencyMap.get(account.getCurrency()));
            cell.setCellStyle(style);
            rowCounter++;
        }
        for (int i = 0; i < this.sheet.getRow(0).getPhysicalNumberOfCells(); i++) {
            this.sheet.autoSizeColumn(i);
        }
    }
}
