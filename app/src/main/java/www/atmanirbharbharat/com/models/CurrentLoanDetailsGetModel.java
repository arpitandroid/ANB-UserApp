package www.atmanirbharbharat.com.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class CurrentLoanDetailsGetModel {
    @SerializedName("status")
    private int status;

    @SerializedName("error")
    private String error;

    @SerializedName("data")
    private Data data;

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public Data getData() {
        return data;
    }

    public class Data implements Serializable {
        @SerializedName("basic_detail")
        private LoanBasicDetails loanBasicDetails;

        public LoanBasicDetails getLoanBasicDetails() {
            return loanBasicDetails;
        }

    }
}
