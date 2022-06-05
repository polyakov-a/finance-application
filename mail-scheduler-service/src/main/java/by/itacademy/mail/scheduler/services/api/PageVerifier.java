package by.itacademy.mail.scheduler.services.api;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageVerifier {

    public static Pageable createPageable(Integer page, Integer size) {

        Pageable pageable = null;
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 20;
        }
        if (page >= 0 && size > 0) {
            pageable = PageRequest.of(page, size);
        }
        String error = "";
        if (page < 0) {
            error = "Page must be >= 0";
        }
        if (size <= 0) {
            if (!error.isEmpty()) {
                error += ", ";
            }
            error += "Size must be > 0";
        }
        if (!error.isEmpty()) {
            throw new IllegalArgumentException(error);
        }
        return pageable;
    }
}
