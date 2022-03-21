package www.atmanirbharbharat.com.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Panelty implements Serializable {

    @SerializedName("days")
    private String days;

    @SerializedName("amount")
    private String amount;

    public String getDays() {
        return days;
    }

    public String getAmount() {
        return amount;
    }
}
