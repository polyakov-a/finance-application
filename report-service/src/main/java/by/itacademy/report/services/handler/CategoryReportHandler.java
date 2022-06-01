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
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryReportHandler implements IReportHandler<Operation> {

    private final OperationRestService operationRestService;
    private final CurrencyRestService currencyRestService;
    private final OperationCategoryRestService operationCategoryRestService;
    private final Workbook workbook;
    private final Sheet sheet;


    public CategoryReportHandler(OperationRestService operationRestService,
                                 CurrencyRestService currencyRestService,
                                 OperationCategoryRestService operationCategoryRestService) {
        this.operationRestService = operationRestService;
        this.currencyRestService = currencyRestService;
        this.operationCategoryRestService = operationCategoryRestService;
        this.workbook = new HSSFWorkbook();
        this.sheet = workbook.createSheet("category report");
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

        Cell cell = row.createCell(0);
        cell.setCellValue("category");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("date and time");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("description");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("value");
        cell.setCellStyle(style);

        cell = row.createCell(4);
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

        Map<String, List<Operation>> map = new HashMap<>();
        for (Operation operation : data) {
            UUID categoryId = operation.getCategory();
            String category = categoryMap.get(categoryId);
            if (map.get(category) == null) {
                List<Operation> operations = new ArrayList<>();
                operations.add(operation);
                map.put(category, operations);
            } else {
                List<Operation> operations = map.get(category);
                operations.add(operation);
                map.put(category, operations);
            }
        }
        int rowCounter = 1;
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setFont(font);
        style.setDataFormat(this.workbook.createDataFormat().getFormat(("YYYY-MM-DD hh:mm:ss")));
        for (Map.Entry<String, List<Operation>> entry : map.entrySet()) {
            String category = entry.getKey();
            Row row = this.sheet.createRow(rowCounter++);
            Cell cell = row.createCell(0);
            cell.setCellValue(category);
            cell.setCellStyle(style);
            for (Operation operation : entry.getValue()) {
                row = this.sheet.createRow(rowCounter++);
                cell = row.createCell(1);
                cell.setCellValue(operation.getDate());
                cell.setCellStyle(style);

                cell = row.createCell(2);
                cell.setCellValue(operation.getDescription());
                cell.setCellStyle(style);

                cell = row.createCell(3);
                cell.setCellValue(operation.getValue().toString());
                cell.setCellStyle(style);

                cell = row.createCell(4);
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
