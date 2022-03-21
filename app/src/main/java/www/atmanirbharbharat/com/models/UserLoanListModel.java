package www.atmanirbharbharat.com.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserLoanListModel {
    @SerializedName("status")
    private int status;

    @SerializedName("error")
    private String error;

    @SerializedName("data")
    private ArrayList<LoanBasicDetails> data;

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public ArrayList<LoanBasicDetails> getData() {
        return data;
    }
}
