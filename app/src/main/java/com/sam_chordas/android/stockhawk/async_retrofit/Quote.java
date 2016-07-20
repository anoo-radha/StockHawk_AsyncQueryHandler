
package com.sam_chordas.android.stockhawk.async_retrofit;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Quote {

    @SerializedName("Symbol")
    @Expose
    private String symbol;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Open")
    @Expose
    private String open;
    @SerializedName("High")
    @Expose
    private String high;
    @SerializedName("Low")
    @Expose
    private String low;
    @SerializedName("Close")
    @Expose
    private String close;
    @SerializedName("Volume")
    @Expose
    private String volume;
    @SerializedName("Adj_Close")
    @Expose
    private String adj_Close;

    /**
     * 
     * @return
     *     The symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * 
     * @param symbol
     *     The Symbol
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The Date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 
     * @return
     *     The open
     */
    public String getOpen() {
        return open;
    }

    /**
     * 
     * @param open
     *     The Open
     */
    public void setOpen(String open) {
        this.open = open;
    }

    /**
     * 
     * @return
     *     The high
     */
    public String getHigh() {
        return high;
    }

    /**
     * 
     * @param high
     *     The High
     */
    public void setHigh(String high) {
        this.high = high;
    }

    /**
     * 
     * @return
     *     The low
     */
    public String getLow() {
        return low;
    }

    /**
     * 
     * @param low
     *     The Low
     */
    public void setLow(String low) {
        this.low = low;
    }

    /**
     * 
     * @return
     *     The close
     */
    public String getClose() {
        return close;
    }

    /**
     * 
     * @param close
     *     The Close
     */
    public void setClose(String close) {
        this.close = close;
    }

    /**
     * 
     * @return
     *     The volume
     */
    public String getVolume() {
        return volume;
    }

    /**
     * 
     * @param volume
     *     The Volume
     */
    public void setVolume(String volume) {
        this.volume = volume;
    }

    /**
     * 
     * @return
     *     The adj_Close
     */
    public String getAdj_Close() {
        return adj_Close;
    }

    /**
     * 
     * @param adj_Close
     *     The Adj_Close
     */
    public void setAdj_Close(String adj_Close) {
        this.adj_Close = adj_Close;
    }

}
