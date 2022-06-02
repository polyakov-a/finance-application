package by.itacademy.mail.dto;

import by.itacademy.mail.dto.api.ReportType;

import java.util.Map;

public class MailWrapper {

    private Mail mail;
    private ReportType type;
    private Map<String, Object> params;

    public MailWrapper() {
    }

    public MailWrapper(Mail mail,
                       ReportType type,
                       Map<String, Object> params) {
        this.mail = mail;
        this.type = type;
        this.params = params;
    }

    public Mail getMail() {
        return mail;
    }

    public ReportType getType() {
        return type;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
