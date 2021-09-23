package com.brandmaker.cs.skyhigh.tdb.dto;

import com.brandmaker.cs.skyhigh.tdb.utils.Enumerations;

public class ParseMessageDto {
    private Enumerations.ProcessOutcomeStatusEnum loadStatus;
    private String message;

    public Enumerations.ProcessOutcomeStatusEnum getLoadStatus() {
        return loadStatus;
    }

    public void setLoadStatus(Enumerations.ProcessOutcomeStatusEnum loadStatus) {
        this.loadStatus = loadStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ParseMessageDto(Enumerations.ProcessOutcomeStatusEnum loadStatus, String message) {
        this.loadStatus = loadStatus;
        this.message = message;
    }
}
