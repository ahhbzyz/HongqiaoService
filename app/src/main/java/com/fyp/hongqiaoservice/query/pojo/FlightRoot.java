package com.fyp.hongqiaoservice.query.pojo;

import java.util.List;

/**
 * Created by Administrator on 2015/2/9.
 */
public class FlightRoot {

    private String reason;

    private List<Flight> result ;

    private int error_code;

    public FlightRoot() {
    }

    public FlightRoot(String reason, List<Flight> result, int error_code) {
        this.reason = reason;
        this.result = result;
        this.error_code = error_code;
    }

    @Override
    public String toString() {
        return "FlightRoot{" +
                "reason='" + reason + '\'' +
                ", result=" + result +
                ", error_code=" + error_code +
                '}';
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<Flight> getResult() {
        return result;
    }

    public void setResult(List<Flight> result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
}
