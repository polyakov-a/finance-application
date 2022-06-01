package by.itacademy.report.services.handler;

import by.itacademy.report.dto.rest.Category;
import by.itacademy.report.dto.rest.Currency;
import by.itacademy.report.dto.rest.Operation;
import by.itacademy.report.services.handler.api.IReportHandler;
import by.itacademy.report.services.rest.CurrencyRestService;
import by.itacademy.report.services.rest.OperationCategoryRestService;
import by.itacademy.report.services.rest.OperationRestService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DateReportHandler implements IReportHandler<Operation> {

    private final OperationRestService operationRestService;
    private final CurrencyRestService currencyRestService;
    private final OperationCategoryRestService operationCategoryRestService;
    private final Workbook workbook;
    private final Sheet sheet;


    public DateReportHandler(OperationRestService operationRestService,
                             CurrencyRestService currencyRestService,
                             OperationCategoryRestService operationCategoryRestService) {
        this.operationRestService = operationRestService;
        this.currencyRestService = currencyRestService;
        this.operationCategoryRestService = operationCategoryRestService;
        this.workbook = new HSSFWorkbook();
        this.sheet = workbook.createSheet("date report");
    }


    @Override
    public ByteArrayOutputStream handle(Map<String, Object> params) {

        List<String> accounts = (List<String>) params.get("accounts");
        LocalDateTime from = LocalDateTime.ofInstant(Instant.ofEpochMilli((Long) params.get("from")),
                TimeZone.getDefault().toZoneId());
        LocalDateTime to = LocalDateTime.ofInstant(Instant.ofEpochMilli((Long) params.get("to")),
                TimeZone.getDefault().toZoneId());
        List<String> categories = (List<String>) params.get("categories");
        List<UUID> accountIDs = accounts
                .stream()
                .map(UUID::fromString)
                .collect(Collectors.toList());
        List<UUID> categoryIDs = categories
                .stream()
                .map(UUID::fromString)
                .collect(Collectors.toList());
        List<Operation> rawOperations = this.operationRestService.getAllByIDs(accountIDs);
        List<Operation> operations = this.filter(rawOperations, from, to, categoryIDs);
        this.createHeaders();
        this.createData(operations);

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
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        row.setRowStyle(style);

        Cell cell = row.createCell(0);
        cell.setCellValue("date");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("time");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("description");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("category");
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("value");
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue("currency");
        cell.setCellStyle(style);
    }

    @Override
    public void createData(Collection<Operation> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null");
        }
        List<UUID> currencyIDs = data
                .stream()
                .map(Operation::getCurrency)
                .distinct()
                .collect(Collectors.toList());
        List<Currency> currencies = this.currencyRestService.getAllByIDs(currencyIDs);
        Map<UUID, String> currencyMap = currencies
                .stream()
                .collect(Collectors.toMap(Currency::getUuid, Currency::getTitle));

        List<UUID> categoryIDs = data
                .stream()
                .map(Operation::getCategory)
                .distinct()
                .collect(Collectors.toList());
        List<Category> categories = this.operationCategoryRestService.getAllByIDs(categoryIDs);
        Map<UUID, String> categoryMap = categories
                .stream()
                .collect(Collectors.toMap(Category::getUuid, Category::getTitle));

        Map<LocalDate, List<Operation>> map = new HashMap<>();
        for (Operation operation : data) {
            LocalDate date = operation.getDate().toLocalDate();
            if (map.get(date) == null) {
                List<Operation> operations = new ArrayList<>();
                operations.add(operation);
                map.put(date, operations);
            } else {
                List<Operation> operations = map.get(date);
                operations.add(operation);
                map.put(date, operations);
            }
        }
        int rowCounter = 1;
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setFont(font);
        for (Map.Entry<LocalDate, List<Operation>> entry : map.entrySet()) {
            LocalDate date = entry.getKey();
            Row row = this.sheet.createRow(rowCounter++);
            Cell cell = row.createCell(0);
            cell.setCellValue(date.toString());
            cell.setCellStyle(style);
            for (Operation operation : entry.getValue()) {
                row = this.sheet.createRow(rowCounter++);
                cell = row.createCell(1);
                cell.setCellValue(operation.getDate().toLocalTime().truncatedTo(ChronoUnit.SECONDS).toString());
                cell.setCellStyle(style);

                cell = row.createCell(2);
                cell.setCellValue(operation.getDescription());
                cell.setCellStyle(style);

                cell = row.createCell(3);
                cell.setCellValue(categoryMap.get(operation.getCategory()));
                cell.setCellStyle(style);

                cell = row.createCell(4);
                cell.setCellValue(operation.getValue().toString());
                cell.setCellStyle(style);

                cell = row.createCell(5);
                cell.setCellValue(currencyMap.get(operation.getCurrency()));
                cell.setCellStyle(style);
            }
        }
        for (int i = 0; i < this.sheet.getRow(0).getPhysicalNumberOfCells(); i++) {
            this.sheet.autoSizeColumn(i);
        }

    }

    public List<Operation> filter(List<Operation> operations, LocalDateTime from, LocalDateTime to, List<UUID> categoryIds) {
        return operations
                .stream()
                .filter(operation -> operation.getDate().isAfter(from)
                        && operation.getDate().isBefore(to)
                        && categoryIds.contains(operation.getCategory()))
                .collect(Collectors.toList());
    }
}
