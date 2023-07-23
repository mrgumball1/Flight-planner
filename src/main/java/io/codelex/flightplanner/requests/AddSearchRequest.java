package io.codelex.flightplanner.requests;

import jakarta.validation.constraints.NotBlank;

public class AddSearchRequest {
    @NotBlank
    private String from;
    @NotBlank
    private String to;
    private String departuresDate;

    public AddSearchRequest(String from, String to, String departuresDate) {
        this.from = from;
        this.to = to;
        this.departuresDate = departuresDate;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDeparturesDate() {
        return departuresDate;
    }

    public void setDeparturesDate(String departuresDate) {
        this.departuresDate = departuresDate;
    }
}
