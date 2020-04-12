package co.stxanalyzer.entity;

import java.util.Date;

public class Transaction {
    private String stockId;
    private String interval;
    private double open;
    private double high;
    private double low;
    private double close;
    private long volume;

    public Transaction(String stockId, String interval, double open, double high, double low, double close, long volume) {
        this.stockId = stockId;
        this.interval = interval;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    public Transaction(){};

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

//    public boolean isValueUp(){
//        return open < close;
//    }

//    public double getDailyChangePercentage(){
//        if(isValueUp())
//            return 100-open/close*100;
//        return 100-close/open*100;
//
//    }

    @Override
    public String toString() {
        return "Stock{" +
                ", stockId='" + stockId + '\'' +
                ", interval='" + interval + '\'' +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", volume=" + volume +
                '}';
    }

//    @Override
//    public int compareTo(Stock stock) {
//        return (int) (this.getDailyChangePercentage() - stock.getDailyChangePercentage());
//    }
}
