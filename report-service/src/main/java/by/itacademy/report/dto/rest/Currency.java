package by.itacademy.report.dto.rest;

import java.util.UUID;

public class Currency {

    private UUID uuid;
    private String title;

    public Currency() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "uuid=" + uuid +
                ", title='" + title + '\'' +
                '}';
    }
}
