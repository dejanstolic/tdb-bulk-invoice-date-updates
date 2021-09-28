package com.brandmaker.cs.skyhigh.tdb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByPosition;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CrossChargesDto {

    // Name Of The Invoice
    @CsvBindByPosition(position = 0, required = true)
    private String NameOfCharge;

    // Description
    @CsvBindByPosition(position = 1, required = true)
    private String Description;

    // Amount
    @CsvBindByPosition(position = 2, required = true)
    private Float Amount;

    // Currency
    @CsvBindByPosition(position = 3, required = true)
    private String Currency;

    // Publish Date
    @CsvBindByPosition(position = 4, required = true)
    private String AccountingDate;

    // Booking Date
    @CsvBindByPosition(position = 5, required = true)
    private String TransactionDate;

    // Reference Number
    @CsvBindByPosition(position = 6, required = true)
    private String AccountingTransactionId;

    // Cost Type
    @CsvBindByPosition(position = 7, required = true)
    private String CategoryCode;

    // Cost Center
    @CsvBindByPosition(position = 8, required = true)
    private String CostCenterProjId;

    // BK-RC-GL
    @CsvBindByPosition(position = 9, required = false)
    private String BkRcGl;

    // Responsible Person
    @CsvBindByPosition(position = 10, required = true)
    private String CrossChargeState;

    // Element Id
    @CsvBindByPosition(position = 11, required = true)
    private String ElementId;


    public String getNameOfCharge() {return NameOfCharge;}

    public void setNameOfCharge(String nameOfCharge) {NameOfCharge = nameOfCharge;}

    public String getDescription() {return Description;}

    public void setDescription(String description) {Description = description;}

    public Float getAmount() {return Amount;}

    public void setAmount(Float amount) {Amount = amount;}

    public String getCurrency() {return Currency;}

    public void setCurrency(String currency) {Currency = currency;}

    public String getAccountingDate() {return AccountingDate;}

    public void setAccountingDate(String accountingDate) {AccountingDate = accountingDate;}

    public String getTransactionDate() {return TransactionDate;}

    public void setTransactionDate(String transactionDate) {TransactionDate = transactionDate;}

    public String getAccountingTransactionId() {return AccountingTransactionId;}

    public void setAccountingTransactionId(String accountingTransactionId) {AccountingTransactionId = accountingTransactionId;}

    public String getCategoryCode() {return CategoryCode;}

    public void setCategoryCode(String categoryCode) {CategoryCode = categoryCode;}

    public String getCostCenterProjId() {return CostCenterProjId;}

    public void setCostCenterProjId(String costCenterProjId) {CostCenterProjId = costCenterProjId;}

    public String getBkRcGl() {return BkRcGl;}

    public void setBkRcGl(String bkRcGl) {BkRcGl = bkRcGl;}

    public String getCrossChargeState() {return CrossChargeState;}

    public void setCrossChargeState(String crossChargeState) {CrossChargeState = crossChargeState;}

    public String getElementId() {return ElementId;}

    public void setElementId(String elementId) {ElementId = elementId;}
}
