package com.brandmaker.cs.skyhigh.tdb.dto;

import com.brandmaker.cs.skyhigh.tdb.servlets.CrossChargesServlet;
import com.brandmaker.cs.skyhigh.tdb.utils.Utils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.text.ParseException;
import java.util.Date;


/**
 * Invoice Dto
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceDto {

    private Integer id;
    private Date date;
    private Date bookingDate;
    private String name;
    private String jobNumber;
    private String costCenter;
    private String costType;
    private String responsiblePerson;
    private String supplier;
    private String referenceNumber;
    private String number;
    private String note;
    private Boolean paid;
    private Float amount;

    public InvoiceDto(){}

    public InvoiceDto(CrossChargesDto ccItem, boolean paid) throws ParseException {
        this.amount = ccItem.getAmount();

        this.name = ccItem.getNameOfCharge();
        this.responsiblePerson = ccItem.getCrossChargeState();
        this.date = Utils.inputFormat.parse(ccItem.getAccountingDate());
        this.bookingDate = Utils.inputFormat.parse(ccItem.getTransactionDate());
        this.referenceNumber = ccItem.getAccountingTransactionId();
        this.costType = ccItem.getCategoryCode();

        if (!ccItem.getBkRcGl().equals("")) {
            this.costCenter = ccItem.getCostCenterProjId() + " - " + ccItem.getBkRcGl();
        } else {
            this.costCenter = ccItem.getCostCenterProjId();
        }

        this.note = ccItem.getDescription() + " - Cross Charge added by " + CrossChargesServlet.userName + " via Cross Charge Utility";
    }

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}

    public Date getBookingDate() {return bookingDate;}

    public void setBookingDate(Date bookingDate) {this.bookingDate = bookingDate;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getJobNumber() {return jobNumber;}

    public void setJobNumber(String jobNumber) {this.jobNumber = jobNumber;}

    public String getCostCenter() {return costCenter;}

    public void setCostCenter(String costCenter) {this.costCenter = costCenter;}

    public String getCostType() {return costType;}

    public void setCostType(String costType) {this.costType = costType;}

    public String getResponsiblePerson() {return responsiblePerson;}

    public void setResponsiblePerson(String responsiblePerson) {this.responsiblePerson = responsiblePerson;}

    public String getSupplier() {return supplier;}

    public void setSupplier(String supplier) {this.supplier = supplier;}

    public String getReferenceNumber() {return referenceNumber;}

    public void setReferenceNumber(String referenceNumber) {this.referenceNumber = referenceNumber;}

    public String getNumber() {return number;}

    public void setNumber(String number) {this.number = number;}

    public String getNote() {return note;}

    public void setNote(String note) {this.note = note;}

    public Boolean getPaid() {return paid;}

    public void setPaid(Boolean paid) {this.paid = paid;}

    public Float getAmount() {return amount;}

    public void setAmount(Float amount) {this.amount = amount;}
}
