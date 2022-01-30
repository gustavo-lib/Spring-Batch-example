package com.gortiz.batch.springbatch.model;
import lombok.Data;
import java.util.List;

@Data
public class StockInfo {
    private String stockId;
    private String stockName;
    private double stockPrice;
    private double yearlyHigh;
    private double yearlyLow;
    private String address;
    private String sector;
    private String market;

    public StockInfo() {
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public double getYearlyHigh() {
        return yearlyHigh;
    }

    public void setYearlyHigh(double yearlyHigh) {
        this.yearlyHigh = yearlyHigh;
    }

    public double getYearlyLow() {
        return yearlyLow;
    }

    public void setYearlyLow(double yearlyLow) {
        this.yearlyLow = yearlyLow;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }
}