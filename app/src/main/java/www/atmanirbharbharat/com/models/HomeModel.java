package www.atmanirbharbharat.com.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeModel {
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
        @SerializedName("user")
        private User user;

        @SerializedName("loan_list")
        private ArrayList<LoanList> loanList;

        @SerializedName("current_loan")
        private LoanBasicDetails currentLoan;

        public User getUser() {
            return user;
        }

        public ArrayList<LoanList> getLoanList() {
            return loanList;
        }

        public LoanBasicDetails getCurrentLoan() {
            return currentLoan;
        }
    }




}
