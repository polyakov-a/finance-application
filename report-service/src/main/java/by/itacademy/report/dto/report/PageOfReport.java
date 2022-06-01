package by.itacademy.report.dto.report;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;


@JsonPropertyOrder({"number", "size", "totalPages", "totalElements", "first", "numberOfElements", "last", "content"})
public class PageOfReport {

    private int number;
    private int size;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("total_elements")
    private int totalElements;
    private boolean first;

    @JsonProperty("number_of_elements")
    private int numberOfElements;
    private boolean last;
    private List<Report> content;


    private PageOfReport(int number, int size, int totalPages,
                         int totalElements, boolean first,
                         int numberOfElements, boolean last, List<Report> content) {
        this.number = number;
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.first = first;
        this.numberOfElements = numberOfElements;
        this.last = last;
        this.content = content;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public List<Report> getContent() {
        return content;
    }

    public void setContent(List<Report> content) {
        this.content = content;
    }

    public static PageOfReport of(int number, int size, int totalPages,
                                      int totalElements, boolean first,
                                      int numberOfElements, boolean last, List<Report> content) {
        return new PageOfReport(number, size, totalPages, totalElements, first, numberOfElements, last, content);
    }
}
