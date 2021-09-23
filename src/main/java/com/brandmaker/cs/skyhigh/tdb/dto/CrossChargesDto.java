package com.brandmaker.cs.skyhigh.tdb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByPosition;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CrossChargesDto {

    @CsvBindByPosition(position = 0)
    private String NameOfTheInvoice;

    @CsvBindByPosition(position = 1)
    private String Comment;

    @CsvBindByPosition(position = 2)
    private Float Amount;

    @CsvBindByPosition(position = 3)
    private String Currency;

    @CsvBindByPosition(position = 4)
    private Date PublishDate;

    @CsvBindByPosition(position = 5)
    private Date BookingDate;

    @CsvBindByPosition(position = 6)
    private String ReferenceNumber;

    @CsvBindByPosition(position = 7)
    private String CostType;

    @CsvBindByPosition(position = 8)
    private String CostCenter;

    @CsvBindByPosition(position = 9)
    private String BkRcGl;

    @CsvBindByPosition(position = 10)
    private String ResponsiblePerson;

    @CsvBindByPosition(position = 11)
    private String ElementId;


    public String getNameOfTheInvoice() {return NameOfTheInvoice;}

    public void setNameOfTheInvoice(String nameOfTheInvoice) {NameOfTheInvoice = nameOfTheInvoice;}

    public String getComment() {return Comment;}

    public void setComment(String comment) {Comment = comment;}

    public Float getAmount() {return Amount;}

    public void setAmount(Float amount) {Amount = amount;}

    public String getCurrency() {return Currency;}

    public void setCurrency(String currency) {Currency = currency;}

    public Date getPublishDate() {return PublishDate;}

    public void setPublishDate(Date publishDate) {PublishDate = publishDate;}

    public Date getBookingDate() {return BookingDate;}

    public void setBookingDate(Date bookingDate) {BookingDate = bookingDate;}

    public String getReferenceNumber() {return ReferenceNumber;}

    public void setReferenceNumber(String referenceNumber) {ReferenceNumber = referenceNumber;}

    public String getCostType() {return CostType;}

    public void setCostType(String costType) {CostType = costType;}

    public String getCostCenter() {return CostCenter;}

    public void setCostCenter(String costCenter) {CostCenter = costCenter;}

    public String getBkRcGl() {return BkRcGl;}

    public void setBkRcGl(String bkRcGl) {BkRcGl = bkRcGl;}

    public String getResponsiblePerson() {return ResponsiblePerson;}

    public void setResponsiblePerson(String responsiblePerson) {ResponsiblePerson = responsiblePerson;}

    public String getElementId() {return ElementId;}

    public void setElementId(String elementId) {ElementId = elementId;}
}
